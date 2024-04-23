package pl.inpost.recruitmenttask.data.cache.rook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "shipment_entity")
data class ShipmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "shipmentType") val shipmentType: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "eventLog") val eventLog: List<EventLogEntity>,
    @ColumnInfo(name = "openCode") val openCode: String?,
    @ColumnInfo(name = "expiryDate") val expiryDate: ZonedDateTime?,
    @ColumnInfo(name = "storedDate") val storedDate: ZonedDateTime?,
    @ColumnInfo(name = "pickUpDate") val pickUpDate: ZonedDateTime?,
    @ColumnInfo(name = "receiver") val receiver: CustomerEntity?,
    @ColumnInfo(name = "sender") val sender: CustomerEntity?,
    @ColumnInfo(name = "operations") val operations: OperationsEntity,
    @ColumnInfo(name = "archived") var archived: Boolean = false
)