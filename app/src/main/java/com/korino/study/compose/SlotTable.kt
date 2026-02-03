package com.korino.study.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable

@Composable
@NonRestartableComposable
fun ConditionalText() {
    if (a) {
        Text(a)
    } else {
        Text(b)
    }
}