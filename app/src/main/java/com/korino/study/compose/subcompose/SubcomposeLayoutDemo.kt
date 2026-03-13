package com.korino.study.compose.subcompose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.skydoves.compose.stability.runtime.TraceRecomposition
import kotlinx.coroutines.launch

@TraceRecomposition("SubcomposeLayout 사용")
@Composable
fun SubcomposeLayoutAnimationDemo() {
    val colors = listOf(
        Color(0xffff6f69),
        Color(0xffffcc5c),
        Color(0xff264653),
        Color(0xff2a9d84)
    )

    var isInColumn by remember { mutableStateOf(true) }

    val animatedOffsets = remember {
        colors.map { Animatable(Offset.Zero, Offset.VectorConverter) }
    }

    val coroutineScope = rememberCoroutineScope()

    SubcomposeLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable { isInColumn = !isInColumn }
    ) { constraints ->

        val itemSize = 80.dp.roundToPx() + 16.dp.roundToPx()

        // ⭐ 목표 위치를 직접 계산 (SubcomposeLayout의 한계)
        val targetPositions = colors.mapIndexed { index, _ ->
            if (isInColumn) {
                Offset(
                    x = (constraints.maxWidth - itemSize) / 2f,
                    y = index * itemSize.toFloat()
                )
            } else {
                Offset(
                    x = index * itemSize.toFloat(),
                    y = (constraints.maxHeight - itemSize) / 2f
                )
            }
        }

        // 애니메이션 시작
        targetPositions.forEachIndexed { index, target ->
            coroutineScope.launch {
                animatedOffsets[index].animateTo(target, tween(800))
            }
        }

        // 실제 아이템 측정
        val placeables = subcompose("items") {
            colors.forEach { color ->
                Box(
                    Modifier
                        .padding(8.dp)
                        .size(80.dp)
                        .background(color, RoundedCornerShape(10))
                )
            }
        }.map { it.measure(Constraints.fixed(itemSize, itemSize)) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val offset = animatedOffsets[index].value
                placeable.place(offset.x.toInt(), offset.y.toInt())
            }
        }
    }
}