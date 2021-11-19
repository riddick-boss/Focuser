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
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                }:${TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60}"
                if (lastMsg != msg) {
                    runBlocking {
                        IntervalService.minutesCountDownInterval.emit(msg)
                    }
                }
            }

            override fun onFinish() {
                runBlocking {
                    Log.d("timer", "DownCounter2 onFinish")
                    IntervalService.intervalFinished.emit(true)
                }
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
