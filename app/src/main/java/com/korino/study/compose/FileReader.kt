package com.korino.study.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import java.io.FileReader

// 진단 제지기 (Diagnostic suppression) 예시

interface FileReaderScope {
    fun onFileOpen(): Unit
    fun onFileClosed(): Unit
    fun onLineRead(line: String): Unit
}

object Scope : FileReaderScope {
    override fun onFileOpen() = TODO()
    override fun onFileClosed() = TODO()
    override fun onLineRead(line: String) = TODO()
}

@Composable
fun FileReader(path: String, content: FileReaderScope.(path: String) -> Unit) {
    Column {
        Scope.content(path)
    }
}