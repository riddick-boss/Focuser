package abandonedstudio.app.focuser.service

import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import android.content.Intent
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntervalService : LifecycleService() {

//    @Inject
//    lateinit var baseNotificationBuilder: NotificationCompat.Builder

//    private lateinit var updatedNotificationBuilder: NotificationCompat.Builder

    //    necessary to distinguish service start from resume
    private var wasServiceAlreadyStarted = false

    //    interval fields
    private var hours = 0
    private var minutes = 1
    private var repetitions = 1
    private var breakDuration = 1 //in minutes

    override fun onCreate() {
        super.onCreate()
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
                    }
                }
                IntervalServiceHelper.ACTION_PAUSE_SERVICE -> {
//                    pauseService()
                }
                IntervalServiceHelper.ACTION_END_SERVICE -> {
//                    endService()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

}
