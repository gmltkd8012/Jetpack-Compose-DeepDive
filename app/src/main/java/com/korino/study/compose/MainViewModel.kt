package com.korino.study.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class MainViewModel : ViewModel() {

    // MainScreen 이 이 State 를 직접 읽으므로,
    // 값이 바뀌면 MainScreen 전체가 restart scope 로 recompose 됨
    // → 하위 UnstableUserScreen / StableUserScreen 재호출 보장
    private val _title = mutableStateOf("유저 정보")
    val title: State<String> = _title

    // Unstable: var 프로퍼티를 가진 data class
    private val _user = mutableStateOf(UnStableUser())
    val user: State<UnStableUser> = _user

    // Stable: @Immutable + val 프로퍼티를 가진 data class
    private val _stableUser = mutableStateOf(StableUser())
    val stableUser: State<StableUser> = _stableUser

    // User 데이터는 그대로, title 만 바꿔서 MainScreen recompose 유발
    fun updateTitle() {
        _title.value = "유저 정보 #${UUID.randomUUID().toString().take(4)}"
    }

    // 실제로 User 데이터를 변경
    fun updateUser() {
        val newName = "Korino_${UUID.randomUUID().toString().take(4)}"
        _user.value = _user.value.copy(name = newName)
        _stableUser.value = _stableUser.value.copy(name = newName)
    }
}

// Unstable: var 프로퍼티 + List → Compose가 변경 감지 불가 → Skipping 불가
@Stable
data class UnStableUser(
    val name: String = "Korino",
    val age: Int = 30,
    val friends: List<String> = emptyList()
)

// Stable: @Immutable + val 프로퍼티 → Compose가 equals로 비교 후 Skipping 가능
data class StableUser(
    val name: String = "Korino",
    val age: Int = 30,
)
