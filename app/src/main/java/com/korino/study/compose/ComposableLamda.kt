package com.korino.study.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun Container(content: @Composable () -> Unit) {
    // ...
    content()
    // ...
}

@Composable
fun TestScreen() {
    Container {
        Text("Test")
    }
}
