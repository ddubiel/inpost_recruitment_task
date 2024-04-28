package pl.inpost.recruitmenttask.features.shipments.viewmodel

interface ShipmentActions {
    fun archiveShipment(shipmentNumber: String)
    fun refreshData()
}