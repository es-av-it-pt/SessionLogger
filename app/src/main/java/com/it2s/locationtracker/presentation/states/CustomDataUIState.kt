package com.it2s.locationtracker.presentation.states

import com.it2s.locationtracker.data.dto.DataCollectionRequestDto
import com.it2s.locationtracker.domain.entity.Event
import com.it2s.locationtracker.domain.entity.ScanModeEntity
import com.it2s.locationtracker.domain.entity.SendEventRequest
import com.it2s.locationtracker.domain.entity.Statut

data class CustomDataUIState(
    val isLoading: Boolean = true,
    val referencePoint: Int = 0,
    val pathNumber: Int = 0,
    val isInBus: Boolean = false,
    val selectedMode: Int = 0,
    val scanModes: List<ScanModeEntity> = emptyList(),
    val isScanning: Boolean = false,
    val address: String = "192.168.1.1",
    val port: String = "",
    val trackTimer: Long = System.currentTimeMillis()
) {

    val url: String
        get() {
            return if (port.isEmpty())
                address
            else
                "$address:$port"
        }

    fun toSendEventRequest(event: Event) = with(scanModes[selectedMode]) {
        url to SendEventRequest(
            dataType = mode.lowercase(),
            collectionNumber = pathNumber,
            statut = if (isInBus) Statut.IN else Statut.OUT,
            event = event,
            timer = if (event == Event.START) 0 else ((System.currentTimeMillis() - trackTimer) / 1000)
        )
    }

    fun toDataCollectionRequestModel() = with(scanModes[selectedMode]) {
        url to DataCollectionRequestDto(
            dataType = mode.lowercase(),
            path = if (mode == "Dynamic") pathNumber else null,
            referencePoint = if (mode == "Static") pathNumber else null,
            collectionProgress = isScanning,

            )
    }
}
