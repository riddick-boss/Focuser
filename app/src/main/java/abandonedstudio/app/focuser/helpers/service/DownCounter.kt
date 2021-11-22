package abandonedstudio.app.focuser.helpers.service

import abandonedstudio.app.focuser.service.IntervalService
import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DownCounter @Inject constructor() : CountDown {

    private lateinit var timer: CountDownTimer

    private var lastMsg = ""

    override fun start(durationInMillis: Long) {
        timer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val msg = IntervalServiceHelper.remainingTimeMinutesFromMillisText(millisUntilFinished)
                if (lastMsg != msg) {
                    runBlocking {
                        IntervalService.millisCountDownInterval.emit(millisUntilFinished)
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
