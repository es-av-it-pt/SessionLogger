package com.it2s.locationtracker.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.it2s.locationtracker.R
import com.it2s.locationtracker.domain.entity.ScanModeEntity
import com.it2s.locationtracker.ui.theme.LocationTrackerTheme
import com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables.ListItemPicker
import com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables.NumberPicker

@Composable
fun ConfigurationPanel(
    address: String,
    port: String,
    scanModes: List<ScanModeEntity>,
    selectedMode: Int,
    pathNumber: Int,
    isInBus: Boolean,
    onAddressUpdate: (String) -> Unit = {},
    onPortUpdate: (String) -> Unit = {},
    onScanModeChange: (ScanModeEntity) -> Unit = {},
    onPathNumChange: (Int) -> Unit = {},
    onBusStateChange: (Boolean) -> Unit = {},
    onConfigExpandedChange: () -> Unit = {},

    ) {

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
                    onAddressUpdate,
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
                    onPortUpdate,
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
                ListItemPicker(Modifier.weight(1f),
                    list = scanModes.toList(),
                    value = scanModes[selectedMode],
                    label = { it.mode },
                    onValueChange = { value ->
                        onScanModeChange(value)
                    })
                NumberPicker(
                    Modifier.weight(1f),
                    value = pathNumber,
                    range = 0 until scanModes[selectedMode].pathCount,
                    onValueChange = onPathNumChange
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(

                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    InOutSwitch(isInBus) {
                        onBusStateChange(it)
                    }
                    Text(stringResource(R.string.in_or_out_of_bus))
                }

                FilledIconButton({
                    onConfigExpandedChange()
                }) {
                    Icon(Icons.Default.Check, null)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConfigurationPanelPreview() {
    LocationTrackerTheme {

        ConfigurationPanel(
            "", "", listOf(
                ScanModeEntity("Static", 100),
                ScanModeEntity("Dynamic", 200)
            ),
            selectedMode = 0,
            pathNumber = 0,
            isInBus = true,
        )
    }
}
