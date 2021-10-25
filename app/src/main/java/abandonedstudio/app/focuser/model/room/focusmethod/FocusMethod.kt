package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_method")
data class FocusMethod (

    @ColumnInfo(name = "name")
    var name: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "method_id")
    var methodId: Int? = null
)