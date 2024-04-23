package pl.inpost.recruitmenttask.features.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.cache.ShipmentDao
import pl.inpost.recruitmenttask.data.cache.rook.entities.CustomerEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.EventLogEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.OperationsEntity
import pl.inpost.recruitmenttask.data.cache.rook.entities.ShipmentEntity
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
import java.security.PrivateKey
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShipmentsViewState(emptyList()))
    val uiState: StateFlow<ShipmentsViewState> = _uiState.asStateFlow()

    init {
        refreshData()
        viewModelScope.launch {
            shipmentDao.getShipmentsFlow().collect { shipments ->
                _uiState.update { currentState ->
                    currentState.copy(shipments = shipments!!.asDomainModel(), isRefreshing = false)
                }
            }
        }
    }

    private fun refreshData() = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(isRefreshing = true)
        }
        val shipments = shipmentApi.getShipments()

        shipmentDao.run {
            clearAndInsert(shipments.asEntity())
        }
    }

    fun refresh() {
        refreshData()
    }

    fun archiveShipment(shipmentNumber: String) {
        viewModelScope.launch {
            shipmentDao.run {
                setShipmentAsArchived(shipmentNumber)
            }
        }
    }


}

@JvmName("functionOfKotlin2")
private fun List<ShipmentNetwork>.asEntity(): List<ShipmentEntity> = map { it.asEntity() }

private fun ShipmentNetwork.asEntity() = ShipmentEntity(
    number = number,
    shipmentType = shipmentType,
    status = status,
    eventLog = eventLog.asEntity(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.asEntity(),
    sender = sender?.asEntity(),
    operations = operations.asEntity(),
)
private fun  List<EventLogNetwork>.asEntity(): List<EventLogEntity> {
    return map { it.asEntity() }
}
private fun EventLogNetwork.asEntity() = EventLogEntity(
    name = name,
    date = date,
)
@JvmName("functionOfKotlin3")
private fun  List<ShipmentEntity>.asDomainModel(): List<ShipmentModel> {
    return map { it.asDomainModel() }
}

private fun ShipmentEntity.asDomainModel() = ShipmentModel(
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


@JvmName("functionOfKotlin4")
private fun List<EventLogEntity>.asDomainModel(): List<EventLogModel> = map { it.asDomainModel() }
private fun EventLogEntity.asDomainModel(): EventLogModel = EventLogModel(
    name = name,
    date = date
)
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

private fun CustomerNetwork.asEntity() = CustomerEntity(
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

private fun OperationsNetwork.asEntity() = OperationsEntity(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)


private fun CustomerEntity.asDomainModel() = CustomerModel(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)


private fun OperationsEntity.asDomainModel() = OperationsModel(
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
