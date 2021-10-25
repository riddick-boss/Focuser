package abandonedstudio.app.focuser.di

import abandonedstudio.app.focuser.model.room.LocalDatabase
import abandonedstudio.app.focuser.util.Constants.LOCAL_DATABASE_NAME
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        LOCAL_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFocusMethodDao(db: LocalDatabase) = db.focusMethodDao()

}