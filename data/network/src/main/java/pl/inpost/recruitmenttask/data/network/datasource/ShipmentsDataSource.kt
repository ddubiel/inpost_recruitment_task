package pl.inpost.recruitmenttask.data.network.datasource

import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipmentsDataSource @Inject constructor(
    private val shipmentApi: ShipmentApi
) {

    suspend fun getShipments() = shipmentApi.getShipments()

}