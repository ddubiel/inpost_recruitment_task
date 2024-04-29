package pl.inpost.recruitmenttask.features.shipments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.features.shipments.model.ShipmentsRepository
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val shipmentsRepository: ShipmentsRepository
) : ViewModel(), ShipmentActions {

    private val _uiState = MutableStateFlow(ShipmentsViewState(emptyList()))
    val uiState: StateFlow<ShipmentsViewState> = _uiState.asStateFlow()

    init {
        refreshData()
        viewModelScope.launch {
            shipmentsRepository.observeShipments().collect { shipments ->
                _uiState.update { currentState ->
                    currentState.copy(shipments = shipments, isRefreshing = false)
                }
            }
        }
    }

    override fun refreshData() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isRefreshing = true)
            }
            shipmentsRepository.getAndCacheShipments()
        }
    }

    override fun setShipmentOrder(shipmentsOrder: ShipmentsOrder) {
        _uiState.update { currentState ->
            if (shipmentsOrder == ShipmentsOrder.STATUS) {
                currentState.copy(shipments = currentState.shipments.toList().sortedBy {
                    it.status
                })
            } else {
                currentState.copy(shipments = currentState.shipments.toList().sortedBy {

                    when (shipmentsOrder) {
                        ShipmentsOrder.PICKUP_DATE -> it.pickUpDate?.toString()
                        ShipmentsOrder.EXPIRE_DATE -> it.expiryDate?.toString()
                        ShipmentsOrder.STORED_DATE -> it.storedDate?.toString()
                        ShipmentsOrder.NUMBER -> it.number
                        else -> it.status.ordinal.toString()
                    }
                }
                )
            }
        }
    }

    override fun archiveShipment(shipmentNumber: String) {
        viewModelScope.launch {
            shipmentsRepository.archiveShipment(shipmentNumber)
        }
    }
}
