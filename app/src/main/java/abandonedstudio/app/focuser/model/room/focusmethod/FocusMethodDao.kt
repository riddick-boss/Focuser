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

    @Query("SELECT * FROM focus_method ORDER BY name")
    suspend fun getAllMethods(): List<FocusMethod>

    @Query("SELECT * FROM focus_method WHERE method_id != :favouriteMethodId ORDER BY name")
    suspend fun getAllMethodsWithoutFavourite(favouriteMethodId: Int): List<FocusMethod>

}
