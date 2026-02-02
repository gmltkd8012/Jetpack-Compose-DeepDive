package com.korino.study.compose

import androidx.compose.runtime.Composable

/**
 * 안정 추론 경우
 */
// 1. 필드가 없음
//class Empty
//
//// 2. 모든 필드가 val + 안정적인 타입
//class Test(val id: Int, val description: String)
//
//// 3. hashCode 사용 (계약상 동일 인스턴스는 동일 결과)
//class Foo<T>(val a: Int, b: T) {
//    val c: Int = b.hashCode()
//}
//
//class Poo<T, A>(val a: T, b: A) {
//    val c: Int = b.hashCode()
//}


/**
 * 불안정 추론 경우
 */
// 1. var 사용 (가변)
class User(var name: String)

// 2. 내부 가변 상태
class Counter {
    private var count: Int = 0  // private여도 불안정!
    fun getCount(): Int = count
    fun increment() { count++ }
}

// 3. 인터페이스 (구현체를 알 수 없음)
@Composable
fun <T> MyList(items: List<T>) {}  // List는 ArrayList일 수도 있음

// 4. 제네릭 타입 (런타임 전까지 알 수 없음)
class Wrapper<T>(val value: T)  // T가 뭔지 모름