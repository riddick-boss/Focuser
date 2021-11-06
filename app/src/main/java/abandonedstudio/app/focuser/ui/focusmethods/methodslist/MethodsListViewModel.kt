package abandonedstudio.app.focuser.ui.focusmethods.methodslist

import abandonedstudio.app.focuser.model.datastore.UserLocalPreferences
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MethodsListViewModel @Inject constructor(private val userLocalPreferences: UserLocalPreferences, private val focusMethodRepository: FocusMethodRepository) : ViewModel(){

    fun saveFavouriteMethodId(id: Int){
        viewModelScope.launch {
            userLocalPreferences.saveFavouriteMethodId(id)
        }
    }

    fun getSavedFavouriteMethodId(): Flow<Int?> {
        return userLocalPreferences.savedFavouriteMethodId
    }
}