package abandonedstudio.app.focuser.ui.gloablsettings

import abandonedstudio.app.focuser.helpers.ui.theming.ThemeMode
import abandonedstudio.app.focuser.helpers.ui.theming.Theming
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalSettingsViewModel @Inject constructor(private val theming: Theming) : ViewModel() {

    fun setLightTheme(){
        setTheme(ThemeMode.LIGHT)
    }

    fun setNightTheme(){
        setTheme(ThemeMode.NIGHT)
    }

    fun setAutoTheme(){
        setTheme(ThemeMode.AUTO)
    }

    private fun setTheme(themeMode: ThemeMode){
        viewModelScope.launch {
            theming.setTheme(themeMode)
        }
    }

    fun loadSavedTheme(): Flow<ThemeMode> {
        return theming.loadTheme()
    }

}
