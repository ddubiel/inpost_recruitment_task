package pl.inpost.recruitmenttask.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.cache.rook.entities.ShipmentEntity


@Dao
interface ShipmentDao {

    @Query("SELECT * from shipment_entity where archived == :isArchived")
    suspend fun getShipments(isArchived: Boolean = false): List<ShipmentEntity>

    @Query("SELECT * from shipment_entity where number == :shipmentNumber ")
    suspend fun getShipment(shipmentNumber: String): List<ShipmentEntity>

    @Query("SELECT * from shipment_entity where archived == :isArchived")
    fun getShipmentsFlow(isArchived: Boolean = false): Flow<List<ShipmentEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shipments: List<ShipmentEntity>)

    @Update
    suspend fun update(stationEntity: ShipmentEntity)

    @Delete
    suspend fun delete(stationEntity: ShipmentEntity)

    @Query("DELETE FROM shipment_entity")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsert(shipments: List<ShipmentEntity>) {
        val archivedShipments = getShipments(true)
        deleteAll()
        insert(shipments.onEach {
            it.archived =
                it.number in archivedShipments.map { archivedShipment -> archivedShipment.number }
        })
    }

    suspend fun setShipmentAsArchived(shipmentNumber: String) {
        update(
            getShipment(shipmentNumber).first().copy(
                archived = true
            )
        )
    }

}