package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.helpers.ui.addmethod.ErrorType
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMethodViewModel @Inject constructor(
    private val focusMethodRepository: FocusMethodRepository
) : ViewModel() {

    private val _validationErrorType = MutableStateFlow(mutableListOf(ErrorType.NONE))
    val validationErrorType = _validationErrorType.asStateFlow()

    //    blank
    val name: String = ""

    suspend fun addMethod() {
        val focusMethod = FocusMethod(name)
        focusMethodRepository.insert(focusMethod)
    }

    fun validateData(name: String, intervalsState: Boolean, intervalHours: Int, intervalMinutes: Int) {
        _validationErrorType.value.clear()
        val errorTypeToReturn = mutableListOf<ErrorType>()
        if (name.isBlank()) {
            errorTypeToReturn.add(ErrorType.EMPTY_NAME)
        }
        _validationErrorType.value = errorTypeToReturn
    }

}
