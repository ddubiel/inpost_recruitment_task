package pl.inpost.recruitmenttask.features.shipments.model

import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipmentsRepository @Inject constructor(
    private val shipmentApi: ShipmentApi
) {
    suspend fun getShipments() = shipmentApi.getShipments()
}