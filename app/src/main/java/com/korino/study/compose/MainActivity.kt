package com.korino.study.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.korino.study.compose.ui.theme.JetpackComposeDeepDiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { paddingValues ->
                MainScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // ViewModel State 를 MainScreen 이 직접 읽음
    // → title 이 바뀌면 MainScreen 전체가 recompose → 자식 재호출 보장
    val title by viewModel.title
    val user by viewModel.user
    val stableUser by viewModel.stableUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // User 값은 그대로, title 만 변경 → MainScreen recompose
            // → UnstableUserScreen: Unstable 이므로 Skip 불가 → recompose
            // → StableUserScreen: Stable 이고 값 동일 → Skip
            Button(onClick = { viewModel.updateTitle() }) {
                Text("타이틀만 변경")
            }
            // 실제 User 데이터 변경 → 둘 다 recompose
            Button(onClick = { viewModel.updateUser() }) {
                Text("User 변경")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ParentScreen(
            title = title,
            user = user,
            stableUser = stableUser
        )
    }
}

// 트리거 용 부모 Composable
@Composable
fun ParentScreen(
    title: String,
    user: UnStableUser,
    stableUser: StableUser,
) {
    Column {
        // Unstable User: var 프로퍼티 + List<String> → Skipping 불가
        // 부모가 Recomposition되면 User가 바뀌지 않아도 함께 Recomposition됨
        UnstableUserScreen(user = user)

        // Stable User: @Immutable + val 프로퍼티 → Skipping 가능
        // 부모가 Recomposition되어도 값이 같으면 Recomposition 건너뜀
        StableUserScreen(user = stableUser)
    }
}

@Composable
fun UnstableUserScreen(user: UnStableUser) {
    val count = remember { mutableIntStateOf(0) }
    SideEffect { count.value++ }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Unstable User (var 프로퍼티)",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Recomposition 횟수: ${count.value}",
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("이름: ${user.name}")
            Text("나이: ${user.age}")
            Text("친구: ${user.friends.ifEmpty { listOf("없음") }.joinToString()}")
        }
    }
}

@Composable
fun StableUserScreen(user: StableUser) {
    val count = remember { mutableIntStateOf(0) }
    SideEffect { count.value++ }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Stable User (@Immutable + val 프로퍼티)",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Recomposition 횟수: ${count.value}",
                color = Color(0xFF2E7D32),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("이름: ${user.name}")
            Text("나이: ${user.age}")
        }
    }
}
