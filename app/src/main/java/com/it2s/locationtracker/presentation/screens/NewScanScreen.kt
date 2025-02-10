@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.it2s.locationtracker.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.it2s.locationtracker.R
import com.it2s.locationtracker.core.indexOrNull
import com.it2s.locationtracker.domain.entity.Event
import com.it2s.locationtracker.domain.entity.ScanModeEntity
import com.it2s.locationtracker.presentation.viewModels.CustomDataViewModel
import com.it2s.locationtracker.ui.theme.LocationTrackerTheme
import com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables.ListItemPicker
import com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables.NumberPicker
import com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables.SimpleTimer

@Preview
@Composable
private fun CustomScanPagePreview() {
    LocationTrackerTheme() {
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
//    var trackTimer by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardListener by keyboardAsState()



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
        if (!configExpanded)
            Button({
                if (!isScanning)
                    viewModel.resetTimer()
                viewModel.setIsScanning(!isScanning)
                viewModel.sendEvent(if (isScanning) Event.STOP else Event.START)

            }, modifier = Modifier.fillMaxWidth(.9f), shape = RoundedCornerShape(0.dp)) {
                Text(stringResource(if (isScanning) R.string.stop_scanning else R.string.start_scanning))
            }
    }) { paddingValues ->

        Column(
            Modifier.padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(configExpanded) {
                Card {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                address,
                                { viewModel.updateAddress(it) },
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(.7f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType =
                                    KeyboardType.Number
                                ),
                                singleLine = true,
                                placeholder = {
                                    Text(stringResource(R.string.address_label))
                                })
                            TextField(
                                port,
                                { viewModel.updatePort(it) },
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(.3f),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType =
                                    KeyboardType.Number
                                ),
                                placeholder = {
                                    Text(stringResource(R.string.port_label))
                                })
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ListItemPicker<ScanModeEntity>(Modifier.weight(1f),
                                list = scanModes.toList(),
                                value = scanModes[selectedMode],
                                label = { it.mode },
                                onValueChange = { value ->
                                    viewModel.setSelectedMode(scanModes.indexOrNull(value) ?: 0)
                                })
                            NumberPicker(Modifier.weight(1f),
                                value = pathNumber,
                                range = 0 until scanModes[selectedMode].pathCount,
                                onValueChange = {
                                    viewModel.setPathNumber(it)
                                })
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(

                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Switch(isInBus, { viewModel.setIsInBus(it) })
                                Text(stringResource(R.string.in_or_out_of_bus))
                            }

                            FilledIconButton({
                                configExpanded = viewModel.validateConfig(address, port)
                            }) {
                                Icon(Icons.Default.Check, null)
                            }
                        }
                    }
                }
            }

            if (!configExpanded)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Switch(isInBus, {

                        viewModel.setIsInBus(!isInBus)
                        if (isScanning) {
                            viewModel.sendEvent(Event.CHANGE)
                        }
                    }, Modifier.scale(2f), thumbContent = {
                        Text(if (isInBus) "IN" else "OUT", Modifier/*.rotate(90f)*/, style = MaterialTheme.typography.labelSmall)

                    })
                    Spacer(Modifier.height(16.dp))
                    SimpleTimer(isScanning, trackTimer)

                }


        }
    }

}


@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

