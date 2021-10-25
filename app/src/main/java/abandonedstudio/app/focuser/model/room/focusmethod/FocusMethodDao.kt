package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.room.*

@Dao
interface FocusMethodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(focusMethod: FocusMethod)

    @Update
    suspend fun update(focusMethod: FocusMethod)

    @Delete
    suspend fun delete(focusMethod: FocusMethod)
}