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

    override fun archiveShipment(shipmentNumber: String) {
        viewModelScope.launch {
            shipmentsRepository.archiveShipment(shipmentNumber)
        }
    }
}
