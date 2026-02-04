package com.korino.study.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
//
//@Composable
//@NonRestartableComposable
//fun ConditionalText() {
//    if (a) {
//        Text(a)
//    } else {
//        Text(b)
//    }
//}


@Composable
fun SlotScreen() {
    Text(text = "text")
    Button(onClick = {}) { }
}