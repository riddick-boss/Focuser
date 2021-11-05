package abandonedstudio.app.focuser.util

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Themer @Inject constructor(
    private val userLocalPreferences: UserLocalPreferences
) {

    companion object {
        fun getThemeModeBasedOnString(theme: String?): ThemeMode {
            return when (theme) {
                ThemeMode.LIGHT.toString() -> ThemeMode.LIGHT
                ThemeMode.NIGHT.toString() -> ThemeMode.NIGHT
                else -> ThemeMode.AUTO
            }
        }
    }

    suspend fun setTheme(themeMode: ThemeMode) {
        when (themeMode) {
            ThemeMode.LIGHT -> {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
            ThemeMode.NIGHT -> {
                setDefaultNightMode(MODE_NIGHT_YES)
            }
            ThemeMode.AUTO -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        saveThemeMode(themeMode)
    }

    private suspend fun saveThemeMode(themeMode: ThemeMode) {
        userLocalPreferences.saveThemeMode(themeMode)
    }

    fun loadTheme(): Flow<ThemeMode> {
        return userLocalPreferences.savedThemeMode
    }
}