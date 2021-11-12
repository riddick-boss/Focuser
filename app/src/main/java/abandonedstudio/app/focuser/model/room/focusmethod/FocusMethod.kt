package abandonedstudio.app.focuser.model.room.focusmethod

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_method")
data class FocusMethod (

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "interval_state")
    var intervalState: Boolean,

    @ColumnInfo(name = "interval_hours")
    var intervalHours: Int,

    @ColumnInfo(name = "interval_minutes")
    var intervalMinutes: Int,

    @ColumnInfo(name = "interval_repetitions")
    var intervalRepetitions: Int,

    @ColumnInfo(name = "interval_break")
    var intervalBreak: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "method_id")
    var methodId: Int? = null
)