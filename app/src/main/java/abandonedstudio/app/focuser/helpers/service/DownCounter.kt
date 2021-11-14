package abandonedstudio.app.focuser.helpers.service

import android.os.CountDownTimer

class DownCounter(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {


    override fun onTick(millisUntilFinished: Long) {
        var millis = millisUntilFinished
//TODO
    }

    override fun onFinish() {
        TODO("Not yet implemented")
    }
}