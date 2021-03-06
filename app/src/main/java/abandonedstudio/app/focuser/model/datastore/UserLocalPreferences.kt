package abandonedstudio.app.focuser.model.datastore

import abandonedstudio.app.focuser.helpers.ui.theming.ThemeMode
import abandonedstudio.app.focuser.helpers.ui.theming.Themer
import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences.PreferenceKeys.FAVOURITE_METHOD_ID_KEY
import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences.PreferenceKeys.THEME_MODE_KEY
import abandonedstudio.app.focuser.util.Constants.FAVOURITE_METHOD
import abandonedstudio.app.focuser.util.Constants.THEME_MODE
import abandonedstudio.app.focuser.util.Constants.USER_LOCAL_PREFERENCES_DS
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
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

    private val dataStore = appContext.dataStore

    object PreferenceKeys {
        val THEME_MODE_KEY = stringPreferencesKey(name = THEME_MODE)
        val FAVOURITE_METHOD_ID_KEY = intPreferencesKey(name = FAVOURITE_METHOD)
    }

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit {
            it[THEME_MODE_KEY] = themeMode.toString()
        }
    }

    val savedThemeMode: Flow<ThemeMode> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        Themer.getThemeModeBasedOnString(it[THEME_MODE_KEY])
    }

    suspend fun saveFavouriteMethodId(methodId: Int) {
        dataStore.edit {
            it[FAVOURITE_METHOD_ID_KEY] = methodId
        }
    }

    suspend fun clearFavouriteMethod() {
        dataStore.edit {
            it.remove(FAVOURITE_METHOD_ID_KEY)
        }
    }

    val savedFavouriteMethodId: Flow<Int?> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[FAVOURITE_METHOD_ID_KEY]
    }

}
