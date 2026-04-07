package com.korino.study.compose

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



// 2. @Immutable 적용
@Immutable
data class ImmutableUser(val name: String)

// 3. @Stable 적용
@Stable
data class StableUser(val name: String)


data class User(
    var name: String = "Korino",
    var friends: List<String> = emptyList()
)

@Composable
fun TypeAnnotationScreen(trigger: Int) {

    // trigger 가 바뀔 때마다 TypeAnnotationScreen 이 recomposition
    // → user 가 매번 새 인스턴스로 생성됨 (값은 동일)
    val user = User(name = "Korino", friends = listOf("A", "B"))

    UserCard(user)


}

@Composable
fun UserCard(user: User) {
    SideEffect { Log.d("Recomposition", "UserCard recomposed") }
    Text(user.name)
}

