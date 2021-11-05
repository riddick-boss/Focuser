package abandonedstudio.app.focuser.di

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun userLocalPreferences(@ApplicationContext appContext: Context): UserLocalPreferences =
        UserLocalPreferences(appContext)
}