@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.siliconlabs.bledemo.features.demo.custom_data_collection.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun ExposedDropdownMenuBoxPreview() {
    ExposedDropdownMenuBox(listOf("Static", "Dynamic"), Modifier.fillMaxWidth())
}

@Composable
fun ExposedDropdownMenuBox(
    options: List<String>,
    modifier: Modifier = Modifier,
    initialIndex: Int = -1,
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(initialIndex) }

    Box(modifier) {

        TextField(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(),
            readOnly = true,
            value = options.getOrElse(selectedIndex) { "" },
            onValueChange = { },
            label = { Text("Categories") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        DropdownMenu(
            expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(text = {
                    Row {
                        if (index == selectedIndex) Icon(
                            Icons.Default.Check,
                            contentDescription = null
                        )
                        Text(text = selectionOption)
                    }
                }, onClick = {
                    selectedIndex = index
                    expanded = false
                })
            }
        }

    }
}