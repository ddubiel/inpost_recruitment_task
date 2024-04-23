package pl.inpost.recruitmenttask.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.inpost.recruitmenttask.data.cache.rook.entities.CustomerEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.EventLogEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.OperationsEntity
import java.time.ZonedDateTime

class DBTypeConverters {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun fromZonedDateTimeToString(value: ZonedDateTime?) = value?.toString()

    @TypeConverter
    fun fromStringToZoneDateTime(value: String?) = value?.let {
        ZonedDateTime.parse(value)
    }

    @TypeConverter
    fun fromEventLogEntityListToString(value: List<EventLogEntity>): String = gson.toJson(value)

    @TypeConverter
    fun fromStringToEventLogEntity(value: String): List<EventLogEntity> =
        gson.fromJson(value, object : TypeToken<List<EventLogEntity>>() {}.type)

    @TypeConverter
    fun fromCustomerEntityToString(value: CustomerEntity?): String = value?.let {
        gson.toJson(value)
    } ?: ""

    @TypeConverter
    fun fromStringToCustomerEntity(value: String?): CustomerEntity? = value?.let {
        gson.fromJson(value, object : TypeToken<CustomerEntity>() {}.type)
    }

    @TypeConverter
    fun fromOperationsEntityToString(value: OperationsEntity): String = gson.toJson(value)

    @TypeConverter
    fun fromStringToOperationsEntity(value: String): OperationsEntity =
        gson.fromJson(value, object : TypeToken<OperationsEntity>() {}.type)


}