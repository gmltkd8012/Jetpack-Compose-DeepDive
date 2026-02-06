package com.korino.study.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout

@Composable
fun Test() {
    val slot by remember { mutableStateOf(false) }

    Layout(
        content = TODO(),
        modifier = TODO(),
        measurePolicy = TODO()
    )
}