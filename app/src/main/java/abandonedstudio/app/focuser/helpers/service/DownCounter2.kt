package abandonedstudio.app.focuser.helpers.service

import abandonedstudio.app.focuser.service.IntervalService
import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class DownCounter2(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {


    override fun onTick(millisUntilFinished: Long) {
        val msg = "${TimeUnit.MILLISECONDS.toHours(millisUntilFinished)}:${
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        }"
        runBlocking {
            Log.d("timer", msg)
            IntervalService.minutesCountDownInterval.emit(msg)
        }
    }

    override fun onFinish() {
        Log.d("timer", "DownCounter2 onFinish")
    }
}