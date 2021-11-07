package abandonedstudio.app.focuser.ui.focusmethods.method

import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MethodViewModel @Inject constructor(private val focusMethodRepository: FocusMethodRepository) :
    ViewModel() {

    suspend fun getMethod(methodId: Int): FocusMethod {
        return focusMethodRepository.getMethod(methodId)
    }
}