package abandonedstudio.app.focuser.service

import abandonedstudio.app.focuser.helpers.service.CountDown
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import abandonedstudio.app.focuser.util.Constants
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class IntervalService : LifecycleService() {

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    private lateinit var updatedNotificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var countDown: CountDown

    //    necessary to distinguish service start from resume
    private var wasServiceAlreadyStarted = false

    //    interval fields
    private var hours = 0
    private var minutes = 1
    private var repetitions = 1
    private var breakDuration = 1 //in minutes

    companion object {
        val minutesCountDownInterval = MutableSharedFlow<String>()
//        var hoursCountDownInterval: Int? = null
    }

    override fun onCreate() {
        super.onCreate()

        updatedNotificationBuilder = baseNotificationBuilder
        initializeValues()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                IntervalServiceHelper.ACTION_START_OT_RESUME_SERVICE -> {
                    if (!wasServiceAlreadyStarted) {
                        hours = it.getIntExtra(IntervalServiceHelper.FLAG_HOURS, 0)
                        minutes = it.getIntExtra(IntervalServiceHelper.FLAG_MINUTES, 1)
                        repetitions = it.getIntExtra(IntervalServiceHelper.FLAG_REPETITIONS, 1)
                        breakDuration = it.getIntExtra(IntervalServiceHelper.FLAG_BREAK_DURATION, 1)
                        wasServiceAlreadyStarted = true
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
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        countDown.start(TimeUnit.HOURS.toMillis(hours.toLong()) + TimeUnit.MINUTES.toMillis(minutes.toLong()))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        IntervalServiceHelper.createNotificationChanel(notificationManager)

        startForeground(Constants.INTERVAL_SERVICE_NOTIFICATION_ID, baseNotificationBuilder.build())

        minutesCountDownInterval.asLiveData().observe(this, {
            Log.d("timer", it)
            val notification = updatedNotificationBuilder.setContentText(it)
            notificationManager.notify(
                Constants.INTERVAL_SERVICE_NOTIFICATION_ID,
                notification.build()
            )

        })
    }

    private fun initializeValues() {
        wasServiceAlreadyStarted = false
        hours = 0
        minutes = 1
        repetitions = 1
        breakDuration = 1
    }

    private fun endService() {
        wasServiceAlreadyStarted = false
        initializeValues()
        countDown.finish()
        stopForeground(true)
        stopSelf()
    }

}
