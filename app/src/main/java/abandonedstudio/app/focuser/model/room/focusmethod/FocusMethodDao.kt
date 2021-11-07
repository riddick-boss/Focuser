package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusMethodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(focusMethod: FocusMethod)

    @Update
    suspend fun update(focusMethod: FocusMethod)

    @Delete
    suspend fun delete(focusMethod: FocusMethod)

    @Query("SELECT * FROM focus_method ORDER BY name")
    fun getAllMethods(): Flow<List<FocusMethod>>

    @Query("SELECT * FROM focus_method WHERE method_id != :favouriteMethodId ORDER BY name")
    fun getAllMethodsWithoutFavourite(favouriteMethodId: Int): Flow<List<FocusMethod>>

    @Query("SELECT * FROM focus_method WHERE method_id == :methodId")
    suspend fun getMethod(methodId: Int): FocusMethod

}
