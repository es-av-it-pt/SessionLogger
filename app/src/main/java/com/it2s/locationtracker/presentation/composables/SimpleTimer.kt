package com.it2s.locationtracker.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale

@Preview
@Composable
private fun SimpleTimerPreview() {
    SimpleTimer()
}

@Composable
fun SimpleTimer(isRunning: Boolean = false, startTime: Long = 0L) {
    var elapsedTime by remember { mutableLongStateOf(0L) }


    LaunchedEffect(key1 = isRunning) {
        while (isRunning) {
            delay(10) // Update every 10 milliseconds for smoother animation
            elapsedTime = System.currentTimeMillis() - startTime
        }
    }

    Text(
        text = formatTime(elapsedTime),
        fontSize = 24.sp
    )


}

// Helper function to format time
fun formatTime(timeInMillis: Long): String {
    val hours = (timeInMillis / (1000 * 60 * 60)) % 24
    val minutes = (timeInMillis / (1000 * 60)) % 60
    val seconds = (timeInMillis / 1000) % 60

    return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds)
}
