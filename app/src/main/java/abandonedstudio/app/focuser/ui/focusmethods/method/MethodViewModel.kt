package abandonedstudio.app.focuser.ui.focusmethods.method

import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MethodViewModel @Inject constructor(private val focusMethodRepository: FocusMethodRepository) :
    ViewModel() {

    var name = ""
    var isIntervalInThisMethod = true
    var intervalHours = 0
    var intervalMinutes = 0
    var intervalRepetitions = 1
    var intervalBreak = 1

    suspend fun loadMethod(methodId: Int) {
        val method = focusMethodRepository.getMethod(methodId)
        name = method.name
        isIntervalInThisMethod = method.intervalState
        intervalHours = method.intervalHours
        intervalMinutes = method.intervalMinutes
        intervalRepetitions = method.intervalRepetitions
        intervalBreak = method.intervalBreak
    }

}
