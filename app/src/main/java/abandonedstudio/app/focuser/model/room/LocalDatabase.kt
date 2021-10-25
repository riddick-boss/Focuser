package abandonedstudio.app.focuser.model.room

import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethod
import abandonedstudio.app.focuser.model.room.focusmethod.FocusMethodDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FocusMethod::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun focusMethodDao(): FocusMethodDao
}