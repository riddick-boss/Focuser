package abandonedstudio.app.focuser.model.datastore

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences.PreferenceKeys.THEME_MODE_KEY
import abandonedstudio.app.focuser.util.Constants.THEME_MODE
import abandonedstudio.app.focuser.util.Constants.USER_LOCAL_PREFERENCES_DS
import abandonedstudio.app.focuser.helpers.theming.ThemeMode
import abandonedstudio.app.focuser.helpers.theming.Themer
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = USER_LOCAL_PREFERENCES_DS)

@Singleton
class UserLocalPreferences @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    object PreferenceKeys {
        val THEME_MODE_KEY = stringPreferencesKey(name = THEME_MODE)
    }

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        settingsDataStore.edit {
            it[THEME_MODE_KEY] = themeMode.toString()
        }
    }

    val savedThemeMode: Flow<ThemeMode> = settingsDataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        Themer.getThemeModeBasedOnString(it[THEME_MODE_KEY])
    }
}