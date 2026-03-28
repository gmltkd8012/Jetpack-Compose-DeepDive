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
    val name: String = "Korino",
)

@Composable
fun TypeAnnotationScreen() {
    var user by remember { mutableStateOf(User()) }


    Column {
        UnstableCard(user)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                user = user.copy()
            }
        ) {
            Text("Recomposition 트리거 버튼")
        }
    }
}

@Composable
fun UnstableCard(user: User) {
    SideEffect { Log.d("Recomposition", "UnstableCard recomposed") }
    Column() {
        Text(user.name)
    }
}

@Composable
fun ImmutableCard(user: ImmutableUser) {
    SideEffect { Log.d("Recomposition", "ImmutableCard recomposed") }
    Text(user.name)
}

@Composable
fun StableCard(user: StableUser) {
    SideEffect { Log.d("Recomposition", "StableCard recomposed") }
    Text(user.name)
}
//```
//
//---
//
//## 예상 결과
//
//버튼 클릭 시 `trigger` 값이 바뀌어 `TestScreen` 이 recomposition 되는데:
//```
//UnstableCard recomposed   ← 매번 출력 (skip 안됨)
//ImmutableCard recomposed  ← 최초 1회만 출력
//StableCard recomposed     ← 최초 1회만 출력