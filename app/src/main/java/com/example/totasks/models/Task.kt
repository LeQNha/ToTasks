package nha.kc.kotlincode.models

import java.sql.Timestamp

data class Task (
    var TaskName: String,
    var Type: String,
    var Importance: String,
    var DayOfWeek: String,
    var Duration: Int,
    var StartTime: String,
    var EndTime: String
)