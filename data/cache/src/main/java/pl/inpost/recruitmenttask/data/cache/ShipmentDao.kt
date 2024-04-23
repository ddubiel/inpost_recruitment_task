package pl.inpost.recruitmenttask.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.cache.rook.entities.ShipmentEntity

@Dao
interface ShipmentDao {


    @Query("SELECT * from shipment_entity")
    suspend fun getStations(): List<ShipmentEntity>

    @Query("SELECT * from shipment_entity")
    fun getStationsFlow(): Flow<List<ShipmentEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stations: List<ShipmentEntity>)

    @Update
    suspend fun update(stationEntity: ShipmentEntity)

    @Delete
    suspend fun delete(stationEntity: ShipmentEntity)

}