package abandonedstudio.app.focuser.ui.focusmethods.addmethod

import abandonedstudio.app.focuser.helpers.ui.addmethod.ErrorType
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddMethodViewModel @Inject constructor(
    private val focusMethodRepository: FocusMethodRepository
) : ViewModel() {

    private val _validationErrorType = MutableStateFlow(mutableListOf<ErrorType>())
    val validationErrorType = _validationErrorType.asStateFlow()

    //    blank
    var name: String = ""

    suspend fun addMethod() {
        val focusMethod = FocusMethod(name)
        focusMethodRepository.insert(focusMethod)
    }

    fun validateData(
        name: String,
        intervalsState: Boolean,
        intervalHours: Int,
        intervalMinutes: Int
    ) {
        _validationErrorType.value.clear()
        Log.d("validation", _validationErrorType.value.toString())
        val errorTypeToReturn = mutableListOf<ErrorType>()
        if (name.isBlank()) {
            errorTypeToReturn.add(ErrorType.EMPTY_NAME)
        } else {
            this.name = name
        }
        if (intervalsState) {
            if (intervalHours == 0 && intervalMinutes == 0) {
                errorTypeToReturn.add(ErrorType.INTERVAL_ZERO)
            }
        }

        if (errorTypeToReturn.isEmpty()) {
            errorTypeToReturn.add(ErrorType.NONE)
        }
        _validationErrorType.value = errorTypeToReturn
        Log.d("validation", "after " + _validationErrorType.value.toString())
    }

}
