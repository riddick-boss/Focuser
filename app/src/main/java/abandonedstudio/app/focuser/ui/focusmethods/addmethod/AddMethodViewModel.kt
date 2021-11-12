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

    //    method fields
    //    blank
    var name: String = ""
    var intervalsState = true // true -> interval ON, false -> interval OFF
    var intervalHours = 0
    var intervalMinutes = 0
    var intervalRepetitions = 1
    var intervalBreak = 1 // minute

    suspend fun addMethod() {
        val focusMethod =
            FocusMethod(
                name,
                intervalsState,
                intervalHours,
                intervalMinutes,
                intervalRepetitions,
                intervalBreak
            )
        focusMethodRepository.insert(focusMethod)
    }

    fun validateData(
        name: String,
        intervalsState: Boolean,
        intervalHours: Int,
        intervalMinutes: Int,
        intervalRepetitions: Int,
        intervalBreak: Int
    ) {
        _validationErrorType.value.clear()
        Log.d("validation", _validationErrorType.value.toString())
        val errorTypeToReturn = mutableListOf<ErrorType>()
        if (name.isBlank()) {
            errorTypeToReturn.add(ErrorType.EMPTY_NAME)
        } else {
            this.name = name
        }
        this.intervalsState = intervalsState
//        interval fields
        if (intervalsState) {
            if (intervalHours <= 0 && intervalMinutes <= 0) {
                errorTypeToReturn.add(ErrorType.INTERVAL_ZERO)
            } else {
                this.intervalHours = intervalHours
                this.intervalMinutes = intervalMinutes
                this.intervalRepetitions = intervalRepetitions
            }
            if (intervalBreak <= 0) {
                errorTypeToReturn.add(ErrorType.INTERVAL_BREAK_ZERO)
            } else {
                this.intervalBreak = intervalBreak
            }
        }

        if (errorTypeToReturn.isEmpty()) {
            errorTypeToReturn.add(ErrorType.NONE)
        }
        _validationErrorType.value = errorTypeToReturn
        Log.d("validation", "after " + _validationErrorType.value.toString())
    }

}
