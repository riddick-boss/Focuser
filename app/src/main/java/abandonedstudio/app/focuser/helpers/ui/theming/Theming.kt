package abandonedstudio.app.focuser.helpers.ui.theming

import kotlinx.coroutines.flow.Flow

interface Theming {

    suspend fun setTheme(themeMode: ThemeMode)

    fun loadTheme(): Flow<ThemeMode>
}