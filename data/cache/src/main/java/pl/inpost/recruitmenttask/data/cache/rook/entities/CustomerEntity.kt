package pl.inpost.recruitmenttask.data.cache.rook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_entity")
data class CustomerEntity(
    @PrimaryKey
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?,
    @ColumnInfo(name = "name") val name: String?
)
