package pl.inpost.recruitmenttask.features.shipments.model

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import pl.inpost.recruitmenttask.data.cache.ShipmentDao
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.features.shipments.model.mapper.asDomainModel
import pl.inpost.recruitmenttask.features.shipments.model.mapper.asEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipmentsRepository @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao
) {

    fun observeShipments() =
        shipmentDao.getShipmentsFlow().filterNotNull().map { it.asDomainModel() }

    suspend fun getAndCacheShipments() {
        shipmentDao.clearAndInsert(shipmentApi.getShipments().asEntity())
    }

    suspend fun archiveShipment(shipmentNumber: String) {
        shipmentDao.setShipmentAsArchived(shipmentNumber)
    }
}
