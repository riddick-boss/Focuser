package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FocusMethodRepository @Inject constructor(private val focusMethodDao: FocusMethodDao) {

    @WorkerThread
    suspend fun insert(name: String) {
        focusMethodDao.insert(FocusMethod(name))
    }

    @WorkerThread
    suspend fun update(name: String) {
        focusMethodDao.update(FocusMethod(name))
    }

    @WorkerThread
    suspend fun delete(name: String) {
        focusMethodDao.delete(FocusMethod(name))
    }

    fun getAllMethods(): Flow<List<FocusMethod>> {
        return focusMethodDao.getAllMethods()
    }

    fun getAllMethodsWithoutFavourite(favouriteMethodId: Int): Flow<List<FocusMethod>> {
        return focusMethodDao.getAllMethodsWithoutFavourite(favouriteMethodId)
    }

    suspend fun getFavMethod(favouriteMethodId: Int): FocusMethod{
        return focusMethodDao.getFavMethod(favouriteMethodId)
    }

}
