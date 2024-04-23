package pl.inpost.recruitmenttask.data.cache.rook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operations_entity")
data class OperationsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "manualArchive") val manualArchive: Boolean,
    @ColumnInfo(name = "delete") val delete: Boolean,
    @ColumnInfo(name = "collect") val collect: Boolean,
    @ColumnInfo(name = "highlight") val highlight: Boolean,
    @ColumnInfo(name = "expandAvizo") val expandAvizo: Boolean,
    @ColumnInfo(name = "endOfWeekCollection") val endOfWeekCollection: Boolean
)
