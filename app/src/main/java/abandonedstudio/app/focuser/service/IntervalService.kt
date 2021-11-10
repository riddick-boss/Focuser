package abandonedstudio.app.focuser.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntervalService: LifecycleService() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}