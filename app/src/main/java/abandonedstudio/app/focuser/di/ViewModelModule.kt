package abandonedstudio.app.focuser.di

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences
import abandonedstudio.app.focuser.helpers.ui.theming.Themer
import abandonedstudio.app.focuser.helpers.ui.theming.Theming
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideThemer(userLocalPreferences: UserLocalPreferences): Themer =
        Themer(userLocalPreferences)

    @Provides
    fun provideTheming(themer: Themer): Theming = themer
}