package pl.inpost.recruitmenttask.features.shipments.viewmodel

import pl.inpost.recruitmenttask.features.shipments.model.ShipmentModel

data class ShipmentsViewState(
    val shipments: List<ShipmentModel>,
    val isRefreshing: Boolean = false
)
