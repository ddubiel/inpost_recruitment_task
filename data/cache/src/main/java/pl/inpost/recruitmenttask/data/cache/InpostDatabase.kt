package pl.inpost.recruitmenttask.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.cache.rook.entities.CustomerEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.EventLogEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.OperationsEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.ShipmentEntity

@Database(
    entities = [
        CustomerEntity::class,
        EventLogEntity::class,
        OperationsEntity::class,
        ShipmentEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class InpostDatabase : RoomDatabase() {


    abstract val shipmentDao: ShipmentDao

    companion object {

        private const val DATABASE_NAME = "KoleoAppDatabase"

        @Synchronized
        fun getDatabase(context: Context) =
            Room.databaseBuilder(context, InpostDatabase::class.java, DATABASE_NAME).build()

    }
}