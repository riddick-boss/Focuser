package abandonedstudio.app.focuser.helpers.theming

import kotlinx.coroutines.flow.Flow

interface Theming {

    suspend fun setTheme(themeMode: ThemeMode)

    fun loadTheme(): Flow<ThemeMode>
}