package com.korino.study.compose

import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CountPlate() {
    var count by remember { mutableIntStateOf(0) }
    CountText(
        count = count,
        onClick = { count++ }
    )
}

@Composable
private fun CountText(
    count: Int,
    onClick: () -> Unit,
) {
    Text(
        text = "$count",
        modifier = Modifier.clickable { onClick() }
    )
}