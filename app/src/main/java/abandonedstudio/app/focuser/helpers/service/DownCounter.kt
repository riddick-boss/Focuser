package abandonedstudio.app.focuser.helpers.service

import abandonedstudio.app.focuser.service.IntervalService
import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DownCounter @Inject constructor() : CountDown {

    lateinit var timer: CountDownTimer

    private var lastMsg = ""

    override fun start(durationInMillis: Long) {
        timer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val msg = "${TimeUnit.MILLISECONDS.toHours(millisUntilFinished)}:${
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                }:${TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)}"
                if (lastMsg != msg){
                    runBlocking {
                        Log.d("timer", msg)
                        IntervalService.minutesCountDownInterval.emit(msg)
                    }
                }
            }

            override fun onFinish() {
                Log.d("timer", "DownCounter2 onFinish")
            }
        }
        timer.start()
    }

    override fun finish() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

}
