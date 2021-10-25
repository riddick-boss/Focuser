package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.annotation.WorkerThread
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
}