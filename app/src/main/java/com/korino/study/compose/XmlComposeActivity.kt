package com.korino.study.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.korino.study.compose.ui.theme.JetpackComposeDeepDiveTheme

data class ItemData(
    val id: Int,
    val title: String,
    val description: String
)

class XmlComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // XML 레이아웃 사용
        setContentView(R.layout.activity_xml_compose)

        // 샘플 데이터 10개 생성
        val items = List(10) { index ->
            ItemData(
                id = index,
                title = "아이템 #${index + 1}",
                description = "이것은 Compose + XML 혼합 구조의 샘플 아이템입니다."
            )
        }

        // XML의 ComposeView에 Compose 리스트 설정
        findViewById<ComposeView>(R.id.composeListView).setContent {
            JetpackComposeDeepDiveTheme {
                ComposeLazyList(items)
            }
        }
    }
}

@Composable
fun ComposeLazyList(items: List<ItemData>) {
    // Compose의 LazyColumn
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { item ->
            // AndroidView를 사용해서 XML 아이템 레이아웃 사용
            XmlItemView(item)
        }
    }
}

@Composable
fun XmlItemView(item: ItemData) {
    AndroidView(
        factory = { context ->
            // XML 레이아웃을 inflate
            LayoutInflater.from(context)
                .inflate(R.layout.item_xml, null, false)
        },
        update = { view ->
            // XML 뷰의 내용 업데이트
            view.findViewById<TextView>(R.id.iconText).text = "${item.id + 1}"
            view.findViewById<TextView>(R.id.titleText).text = item.title
            view.findViewById<TextView>(R.id.descriptionText).text = item.description
        }
    )
}
