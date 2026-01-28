package com.korino.study.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 원본 코드
@Composable
fun NamePlate(name: String, lastname: String) {
    NameText(text = name)
}