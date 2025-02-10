@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.it2s.locationtracker.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.it2s.locationtracker.R
import com.it2s.locationtracker.core.indexOrNull
import com.it2s.locationtracker.domain.entity.Event
import com.it2s.locationtracker.presentation.composables.ConfigurationPanel
import com.it2s.locationtracker.presentation.composables.ScannerContent
import com.it2s.locationtracker.presentation.viewModels.CustomDataViewModel
import com.it2s.locationtracker.ui.theme.LocationTrackerTheme

@Preview
@Composable
private fun CustomScanPagePreview() {
    LocationTrackerTheme {
        CustomScanPage(Modifier.fillMaxSize())
    }
}


@Composable
fun CustomScanPage(modifier: Modifier = Modifier, viewModel: CustomDataViewModel = viewModel()) {

    val uiState by viewModel.customDataUIState.collectAsStateWithLifecycle()
    val showError by viewModel.showError.collectAsStateWithLifecycle()
    val scanModes = uiState.scanModes
    val selectedMode = uiState.selectedMode
    val isInBus = uiState.isInBus
    val pathNumber = uiState.pathNumber
    val isScanning = uiState.isScanning
    val address = uiState.address
    val port = uiState.port

    val trackTimer = uiState.trackTimer

    var configExpanded by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(showError) {
        if (showError.isNotEmpty()) snackbarHostState.showSnackbar(showError)
        viewModel.clearError()
    }



    if (uiState.isLoading) Scaffold {

        Box(
            Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            CircularProgressIndicator()
        }
    }
    else Scaffold(modifier, snackbarHost = {
        SnackbarHost(snackbarHostState)
    }, topBar = {
        TopAppBar(
            { },
            actions = {
                IconButton({ configExpanded = true }) {
                    Icon(Icons.Default.Settings, null)
                }
            },
        )
    }, floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {

        Button(
            {
                if (!isScanning)
                    viewModel.resetTimer()
                viewModel.setIsScanning(!isScanning)
                viewModel.sendEvent(if (isScanning) Event.STOP else Event.START)

            }, modifier = Modifier.fillMaxWidth(.9f), shape = RoundedCornerShape(0.dp),
            enabled = !configExpanded
        ) {
            Text(stringResource(if (isScanning) R.string.stop_scanning else R.string.start_scanning))
        }
    }) { paddingValues ->

        Column(
            Modifier.padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(configExpanded) {
                ConfigurationPanel(
                    address = address,
                    port = port,
                    scanModes = scanModes,
                    selectedMode = selectedMode,
                    pathNumber = pathNumber,
                    isInBus = isInBus,
                    onAddressUpdate = { viewModel.updateAddress(it) },
                    onPortUpdate = { viewModel.updatePort(it) },
                    onScanModeChange = {
                        viewModel.setSelectedMode(
                            scanModes.indexOrNull(it) ?: 0
                        )
                    },
                    onBusStateChange = { viewModel.setIsInBus(it) },
                    onPathNumChange = { viewModel.setPathNumber(it) }
                ) {
                    configExpanded = viewModel.validateConfig(address, port)
                }
            }

            if (!configExpanded)
                ScannerContent(
                    isInBus,
                    isScanning,
                    trackTimer,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),

                    ) {
                    viewModel.setIsInBus(!isInBus)
                    if (isScanning) {
                        viewModel.sendEvent(Event.CHANGE)
                    }

                }


        }
    }

}




