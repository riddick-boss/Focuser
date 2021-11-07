package abandonedstudio.app.focuser.ui.focusmethods.methodslist

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MethodsListViewModel @Inject constructor(
    private val userLocalPreferences: UserLocalPreferences,
    private val focusMethodRepository: FocusMethodRepository
) : ViewModel() {

    fun saveFavouriteMethodId(id: Int) {
        viewModelScope.launch {
            userLocalPreferences.saveFavouriteMethodId(id)
        }
    }

    fun clearFavouriteMethod() {
        viewModelScope.launch {
            userLocalPreferences.clearFavouriteMethod()
        }
    }

    fun getSavedFavouriteMethodId(): Flow<Int?> {
        return userLocalPreferences.savedFavouriteMethodId
    }

    private suspend fun getAllMethods(): List<FocusMethod> {
        return focusMethodRepository.getAllMethods().first()
    }

    private suspend fun getAllMethodsWithoutFav(favId: Int): List<FocusMethod> {
        return focusMethodRepository.getAllMethodsWithoutFavourite(favId).first()
    }

    suspend fun getMethods(favId: Int?): List<FocusMethod> {
        return if (favId == null) {
            getAllMethods()
        } else {
            getAllMethodsWithoutFav(favId)
        }
    }

    suspend fun getFavMethod(favId: Int?): FocusMethod? {
        return if (favId == null) {
            null
        } else {
            focusMethodRepository.getMethod(favId)
        }
    }

}