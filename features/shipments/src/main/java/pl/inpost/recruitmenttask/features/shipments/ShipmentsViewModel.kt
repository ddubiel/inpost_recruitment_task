package pl.inpost.recruitmenttask.features.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.features.shipments.model.CustomerModel
import pl.inpost.recruitmenttask.features.shipments.model.EventLogModel
import pl.inpost.recruitmenttask.features.shipments.model.OperationsModel
import pl.inpost.recruitmenttask.features.shipments.model.ShipmentModel
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShipmentsViewState(emptyList()))
    val uiState: StateFlow<ShipmentsViewState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(isRefreshing = true)
        }
        val shipments = shipmentApi.getShipments()
        _uiState.update { currentState ->
            currentState.copy(shipments = shipments.asDomainModel(), isRefreshing = false)
        }
    }

    fun refresh() {
        refreshData()
    }


}

private fun List<ShipmentNetwork>.asDomainModel(): List<ShipmentModel> = map { it.asDomainModel() }

private fun ShipmentNetwork.asDomainModel() = ShipmentModel(
    number = number,
    shipmentType = shipmentType,
    status = ShipmentStatus.valueOf(status),
    eventLog = eventLog.asDomainModel(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.asDomainModel(),
    sender = sender?.asDomainModel(),
    operations = operations.asDomainModel(),
)

@JvmName("functionOfKotlin")
private fun List<EventLogNetwork>.asDomainModel() = map { it.asDomainModel() }

private fun EventLogNetwork.asDomainModel() = EventLogModel(
    name = name,
    date = date
)

private fun CustomerNetwork.asDomainModel() = CustomerModel(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)


private fun OperationsNetwork.asDomainModel() = OperationsModel(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)


data class ShipmentsViewState(
    val shipments: List<ShipmentModel>,
    val isRefreshing: Boolean = false
)
