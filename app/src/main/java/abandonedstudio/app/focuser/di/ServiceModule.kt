package abandonedstudio.app.focuser.di

import abandonedstudio.app.focuser.MainActivity
import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.helpers.service.CountDown
import abandonedstudio.app.focuser.helpers.service.DownCounter
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import abandonedstudio.app.focuser.util.Constants
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideShowMethodFragmentPendingIntent(
        @ApplicationContext context: Context
    ) = PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java).also {
            it.action = IntervalServiceHelper.ACTION_SHOW_METHOD_FRAGMENT
        },
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    )!!

    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, Constants.INTERVAL_SERVICE_NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_twotone_timer_24)
        .setContentTitle(context.getString(R.string.working))
        .setContentText("00:00")
        .setContentIntent(pendingIntent)

    @ServiceScoped
    @Provides
    fun provideCountDown(downCounter: DownCounter): CountDown = downCounter

}
