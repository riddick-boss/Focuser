package abandonedstudio.app.focuser.ui.splashscreen

import abandonedstudio.app.focuser.helpers.theming.ThemeMode
import abandonedstudio.app.focuser.helpers.theming.Theming
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val theming: Theming) : ViewModel() {

    fun loadAndSetSavedTheme() {
        viewModelScope.launch {
            setTheme(loadSavedTheme())
        }
    }

    private suspend fun setTheme(themeMode: ThemeMode) {
        theming.setTheme(themeMode)
    }

    private suspend fun loadSavedTheme(): ThemeMode {
        return theming.loadTheme().first()
    }
}
