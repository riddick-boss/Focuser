package abandonedstudio.app.focuser.service

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.helpers.service.CountDown
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import abandonedstudio.app.focuser.service.alarm.AlarmRingtoneUntilDismiss
import abandonedstudio.app.focuser.util.Constants
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.ceil

@AndroidEntryPoint
class IntervalService : LifecycleService() {

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    private lateinit var updatedNotificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var countDown: CountDown

    //    TODO: injection
    private lateinit var alarm: AlarmRingtoneUntilDismiss

    //    interval fields
    private var _hours = 0
    private var _minutes = 1
    private var _repetitions = 1
    private var _breakDuration = 1 //in minutes

    private var _breaksToTake = ceil(_repetitions.toDouble() / 2.0)
    private var _breakNow = true

    companion object {
        val millisCountDownInterval = MutableSharedFlow<Long>()
        val intervalFinished = MutableSharedFlow<Boolean>()

        //    necessary to distinguish service start from resume
        var wasServiceAlreadyStarted = false

        var repetitionsLeft = MutableSharedFlow<Int>()
    }

    override fun onCreate() {
        super.onCreate()
        alarm = AlarmRingtoneUntilDismiss(this)
        updatedNotificationBuilder = baseNotificationBuilder
        initializeValues()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                IntervalServiceHelper.ACTION_START_OT_RESUME_SERVICE -> {
                    if (!wasServiceAlreadyStarted) {
                        _hours = it.getIntExtra(IntervalServiceHelper.FLAG_HOURS, 0)
                        _minutes = it.getIntExtra(IntervalServiceHelper.FLAG_MINUTES, 1)
                        _repetitions = it.getIntExtra(IntervalServiceHelper.FLAG_REPETITIONS, 1)
                        _breakDuration =
                            it.getIntExtra(IntervalServiceHelper.FLAG_BREAK_DURATION, 1)
                        _breaksToTake = ceil(_repetitions.toDouble() / 2.0)
                        wasServiceAlreadyStarted = true
                        runBlocking {
                            repetitionsLeft.emit(_repetitions)
                        }
                        Log.d("timer", "breaksToTake: $_breaksToTake")
                        Log.d("timer", "Service action START")
                        startForegroundService()
                    }
                }
//                TODO: pause probably won't be needed
                IntervalServiceHelper.ACTION_PAUSE_SERVICE -> {
//                    pauseService()
                }
                IntervalServiceHelper.ACTION_END_SERVICE -> {
                    endService()
                }
                IntervalServiceHelper.ACTION_DISMISS_ALARM -> {
                    Log.d("alarm", "interval action dismiss")
                    alarm.dismissAlarm()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        countDown.start(
            TimeUnit.HOURS.toMillis(_hours.toLong()) + TimeUnit.MINUTES.toMillis(
                _minutes.toLong()
            )
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        IntervalServiceHelper.createNotificationChanel(notificationManager)

        startForeground(Constants.INTERVAL_SERVICE_NOTIFICATION_ID, baseNotificationBuilder.build())

//        for notification
        millisCountDownInterval.asLiveData().observe(this, {
            Log.d("timer", IntervalServiceHelper.remainingTimeMinutesFromMillisText(it)+(TimeUnit.MILLISECONDS.toSeconds(it)%60).toString())
            val notification = updatedNotificationBuilder.setContentText(
                IntervalServiceHelper.remainingTimeMinutesFromMillisText(it)
            )
            notificationManager.notify(
                Constants.INTERVAL_SERVICE_NOTIFICATION_ID,
                notification.build()
            )
        })

//        for starting next interval
        intervalFinished.asLiveData().observe(this, {
            if (_repetitions - 1 < 0) {
                endService()
                Toast.makeText(this, "Intervals finished", Toast.LENGTH_SHORT).show()
            }
            Log.d("timer", "breaksToTake= $_breaksToTake")
            Log.d("timer", "repetitionsLeft= $_repetitions")
            alarm.startAlarm()
            if (_breaksToTake >= 0) {
                if (_breakNow) {
                    Log.d("timer", "break now")
                    countDown.start(TimeUnit.MINUTES.toMillis(_breakDuration.toLong()))
                    updatedNotificationBuilder.setContentTitle(getString(R.string.break_word))
                    _breaksToTake -= 1
                    Log.d("timer", "break taken")
                } else {
                    Log.d("timer", "work now")
                    countDown.start(
                        TimeUnit.HOURS.toMillis(_hours.toLong()) + TimeUnit.MINUTES.toMillis(
                            _minutes.toLong()
                        )
                    )
                    updatedNotificationBuilder.setContentTitle(getString(R.string.work))
                    _repetitions -= 1
                }
                _breakNow = !_breakNow
                CoroutineScope(Dispatchers.Main).launch {
                    repetitionsLeft.emit(_repetitions)
                    Log.d("timer", "repetitions emitted")
                }
//                runBlocking {
//                    repetitionsLeft.emit(_repetitions)
//                }
            }
            Log.d("timer", "keep going")
        })
    }

    private fun initializeValues() {
        wasServiceAlreadyStarted = false
        _hours = 0
        _minutes = 1
        _repetitions = 1
        _breakDuration = 1
    }

    private fun endService() {
        alarm.dismissAlarm()
        wasServiceAlreadyStarted = false
        initializeValues()
        countDown.finish()
        stopForeground(true)
        stopSelf()
    }

}
