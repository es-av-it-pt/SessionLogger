package com.it2s.locationtracker.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InOutSwitch(
    isInBus: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit) = {},
) {
    Switch(isInBus, onCheckedChange, modifier, thumbContent = {
        Text(
            if (isInBus) "IN" else "OUT",
            Modifier/*.rotate(90f)*/,
            style = MaterialTheme.typography.labelSmall.copy(
                if (isInBus) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.background
                }
            )
        )

    })
}

@Preview
@Composable
private fun InOutSwitchPreview() {
    Column {
        InOutSwitch(true)
        InOutSwitch(false)
    }
}