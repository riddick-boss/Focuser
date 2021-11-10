package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FocusMethodRepository @Inject constructor(private val focusMethodDao: FocusMethodDao) {

    @WorkerThread
    suspend fun insert(focusMethod: FocusMethod) {
        focusMethodDao.insert(focusMethod)
    }

    @WorkerThread
    suspend fun update(focusMethod: FocusMethod) {
        focusMethodDao.update(focusMethod)
    }

    @WorkerThread
    suspend fun delete(focusMethod: FocusMethod) {
        focusMethodDao.delete(focusMethod)
    }

    fun getAllMethods(): Flow<List<FocusMethod>> {
        return focusMethodDao.getAllMethods()
    }

    fun getAllMethodsWithoutFavourite(favouriteMethodId: Int): Flow<List<FocusMethod>> {
        return focusMethodDao.getAllMethodsWithoutFavourite(favouriteMethodId)
    }

    suspend fun getMethod(methodId: Int): FocusMethod{
        return focusMethodDao.getMethod(methodId)
    }

}
