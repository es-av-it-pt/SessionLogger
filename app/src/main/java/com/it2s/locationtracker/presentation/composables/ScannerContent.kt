package com.it2s.locationtracker.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScannerContent(
    isInBus: Boolean,
    isScanning: Boolean,
    trackTimer: Long,
    modifier: Modifier = Modifier,
    onInOutChange: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier,
        Arrangement.Center, Alignment.CenterHorizontally,
    ) {
        InOutSwitch(isInBus, Modifier.scale(2f), onInOutChange)
        Spacer(Modifier.height(16.dp))
        SimpleTimer(isScanning, trackTimer)

    }
}

@Preview(showBackground = true)
@Composable
private fun ScContentPreview() {
    ScannerContent(true, true, 0L, modifier = Modifier.wrapContentSize().padding(16.dp))

}