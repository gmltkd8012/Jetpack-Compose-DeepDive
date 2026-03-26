package com.korino.study.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.StableMarker
import androidx.compose.runtime.remember

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


@StableMarker
data class User(
    val name: String,
    val age: Int,
)

@Composable
fun SlotScreen() {
    val count = remember { 3 }
    Text(text = "text")
}