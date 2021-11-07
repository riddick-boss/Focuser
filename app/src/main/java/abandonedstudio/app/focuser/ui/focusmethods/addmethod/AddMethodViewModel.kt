package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMethodViewModel @Inject constructor(
    private val focusMethodRepository: FocusMethodRepository
) : ViewModel() {

    fun addMethod(name: String) {
        viewModelScope.launch {
            focusMethodRepository.insert(name)
        }
    }

}
