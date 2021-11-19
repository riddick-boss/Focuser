package abandonedstudio.app.focuser.helpers.service

interface CountDown {

    fun start(durationInMillis: Long)

    fun finish()
}
