package com.korino.study.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


/**
 * 타입 검사 (Type Checks)
 */
@Composable
fun Parent(content: @Composable () -> Unit) {
    content()
}

val normalLamda: () -> Unit = {}


/**
 * 선언 검사
 */
//@Composable
//suspend fun InvalidFuction() {}
//
//@Composable
//fun main() {}
//
///**
// * 진단 제지기 예제
// */
//@Target(AnnotationTarget.FUNCTION)
//annotation class TestAnnotation
//
//inline fun testFunction(a: Int, f: (Int) -> String): String = f(a)
//
//fun main() {
//    testFunction(1) @TestAnnotation { it.toString() }
//}

@Composable
inline fun MyComposable(
    @StringRes nameResId: Int,
    resolver: (Int) -> String
) {
    val name = resolver(nameResId)
    Text(name)
}

@Composable
fun Screen() {
    // 호출 시점에 @Composable 어노테이션 추가 가능!
    MyComposable(nameResId = R.string.app_name) @Composable {
        LocalContext.current.resources.getString(it)
    }
}


// 일반 Kotlin에서는 오류
fun process(action: (path: String) -> Unit) { } // 오류: "함수 유형에는 매개변수에 명명하는 것이 허용되지 않습니다"


//interface FileReaderScope {
//    fun onFileOpen(): Unit
//    fun onFileClosed(): Unit
//    fun onLineRead(line: String): Unit
//}
//
//@Composable
//fun FileReader(
//    path: String,
//    // path라는 이름 지정 가능!
//    content: @Composable FileReaderScope.(path: String) -> Unit
//) {
//    Column {
//        Scope.content(path = path)
//    }
//}