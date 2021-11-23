package abandonedstudio.app.focuser.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager

class Alarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "interval: alarm")
        wl.acquire(1 * 60 * 1000L /*1 minute*/)

//        TODO: do sth here
        wl.release()
    }

//    TODO: methods to start and stop alarm and reciv intent from interval service
}
