package com.it2s.locationtracker.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.core.ipRegex
import com.it2s.locationtracker.core.portRegex
import com.it2s.locationtracker.domain.entity.Event
import com.it2s.locationtracker.domain.useCase.GetScanModeUseCase
import com.it2s.locationtracker.domain.useCase.SendEventUseCase
import com.it2s.locationtracker.domain.useCase.UpdateMQTTUseCase
import com.it2s.locationtracker.domain.useCase.UpdateThunderBoardsUseCase
import com.it2s.locationtracker.presentation.states.CustomDataUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomDataViewModel @Inject constructor(
    private val getScanModeUseCase: GetScanModeUseCase,
    private val updateThunderBoardsUseCase: UpdateThunderBoardsUseCase,
    private val updateMQTTUseCase: UpdateMQTTUseCase,
    private val sendEventUseCase: SendEventUseCase,
) : ViewModel() {

    private val _customDataUIState = MutableStateFlow(CustomDataUIState())
    val customDataUIState: StateFlow<CustomDataUIState> get() = _customDataUIState

    private val _showError = MutableStateFlow("")
    val showError: StateFlow<String> get() = _showError


    fun clearError() {
        _showError.value = ""
    }

    init {
        //load scan modes and their path count

        getScanModeUseCase.invoke(Unit).onEach { state ->
            when (state) {
                is NetworkResult.Error -> _customDataUIState.value =
                    _customDataUIState.value.copy(isLoading = false)

                NetworkResult.Loading -> _customDataUIState.update {
                    it.copy(isLoading = true)
                }

                is NetworkResult.Success -> _customDataUIState.update {
                    it.copy(isLoading = false, scanModes = state.result)
                }
            }
//            _scanModes.value = list
        }.launchIn(viewModelScope)
    }


    /* ---------          Setters to update values        --------- */
    // Function to set the reference point
    fun setReferencePoint(value: Int) {
        _customDataUIState.update {
            it.copy(
                referencePoint = value
            )
        }
    }

    // Function to set the path number
    fun setPathNumber(value: Int) {
        _customDataUIState.update {
            it.copy(
                pathNumber = value
            )
        }
    }

    fun setIsInBus(value: Boolean) {
        _customDataUIState.update {
            it.copy(
                isInBus = value
            )
        }
    }

    // Method to set the selected mode
    fun setSelectedMode(mode: Int) {

        _customDataUIState.update {
            it.copy(
                selectedMode = mode, pathNumber = 0
            )
        }
    }

    // Method to set the selected mode
    fun setIsScanning(isScanning: Boolean) {

        _customDataUIState.update {
            it.copy(
                isScanning = isScanning
            )
        }
    }


    fun updateThunderBoards() {

        updateThunderBoardsUseCase.invoke(customDataUIState.value.toDataCollectionRequestModel())
            .onEach { apiState ->
                when (apiState) {
                    is NetworkResult.Error -> {
                        _showError.value = apiState.message
                        _customDataUIState.value = _customDataUIState.value.copy(isLoading = false)
                    }

                    NetworkResult.Loading -> _customDataUIState.update {
                        it.copy(isLoading = true)
                    }

                    is NetworkResult.Success -> _customDataUIState.update {
                        it.copy(isLoading = false)
                    }

                }
            }.launchIn(viewModelScope)
    }

    fun sendEvent(event: Event) {

        sendEventUseCase.invoke(customDataUIState.value.toSendEventRequest(event))
            .onEach { apiState ->
                when (apiState) {
                    is NetworkResult.Error -> {
                        _showError.value = apiState.message
                        _customDataUIState.value = _customDataUIState.value.copy(isLoading = false)
                    }

                    NetworkResult.Loading -> _customDataUIState.update {
                        it.copy(isLoading = true)
                    }

                    is NetworkResult.Success -> _customDataUIState.update {
                        it.copy(isLoading = false)
                    }

                }
            }.launchIn(viewModelScope)
    }

    fun setAddress(address: String) {
        _customDataUIState.update {
            it.copy(address = address)
        }
    }

    fun setPort(port: String) {
        _customDataUIState.update {
            it.copy(port = port)
        }
    }

    fun validateConfig(address: String, port: String): Boolean {
        if (ipRegex.toRegex().matches(address) && (port.isEmpty() || portRegex.toRegex()
                .matches(port))
        ) {

            _customDataUIState.update {
                it.copy(address = address, port = port)
            }
            return false
        } else {
            _showError.value = "Invalid address or port"

            return true
        }

    }

    fun updateMQTT() {
        updateMQTTUseCase.invoke(Unit).onEach { apiState ->
            when (apiState) {
                is NetworkResult.Error -> _customDataUIState.value =
                    _customDataUIState.value.copy(isLoading = false)

                NetworkResult.Loading -> _customDataUIState.update {
                    it.copy(isLoading = true)
                }

                is NetworkResult.Success -> _customDataUIState.update {
                    it.copy(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetTimer() {
        _customDataUIState.update {
            it.copy(trackTimer = System.currentTimeMillis())
        }
    }

    fun updateAddress(address: String) {
        _customDataUIState.update {
            it.copy(address = address)
        }
    }

    fun updatePort(port: String) {
        _customDataUIState.update {
            it.copy(port = port)
        }
    }
}


