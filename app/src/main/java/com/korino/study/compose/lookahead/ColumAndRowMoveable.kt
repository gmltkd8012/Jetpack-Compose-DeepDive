package com.korino.study.compose.lookahead

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import com.korino.study.compose.AnimatePlacementNodeElement
import com.skydoves.compose.stability.runtime.TraceRecomposition

@Composable
fun LookaheadExampleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        Text(text = "일반 Composable")
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            SimpleMovableContent()
        }

        Text(text = "LookaheadScope 적용된 Composable")
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LookAheadMovableContent()
        }
    }
}

@TraceRecomposition("일반 Composable")
@Composable
fun  SimpleMovableContent () {
    val colors = listOf(
        Color( 0xffff6f69 ),
        Color( 0xffffcc5c ),
        Color( 0xff264653 ),
        Color( 0xFF679138 ),
    )
    var isInColumn by remember { mutableStateOf( true ) }
    val items = remember {
        movableContentOf {
            colors.forEach { color ->
                Box(
                    Modifier.padding
                        ( 8.dp )
                        .size( 80.dp )
                        .background(color, RoundedCornerShape( 10 ))
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { isInColumn = !isInColumn },
        contentAlignment = Alignment.Center
    ) {
        if (isInColumn) {
            Column { items() }
        } else {
            Row { items() }
        }
    }
}

@TraceRecomposition("LookaheadScope 적용된 Composable")
@Composable
fun  LookAheadMovableContent () {
    val colors = listOf(
        Color( 0xffff6f69 ),
        Color( 0xffffcc5c ),
        Color( 0xff264653 ),
        Color( 0xFF679138 ),
    )
    var isInColumn by remember { mutableStateOf( true ) }

    LookaheadScope {
        val items = remember {
            movableContentOf {
                colors.forEach { color ->
                    Box(
                        Modifier.padding
                            ( 8.dp )
                            .size( 80.dp )
                            .then(AnimatePlacementNodeElement(this@LookaheadScope))
                            .background(color, RoundedCornerShape( 10 ))
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().clickable { isInColumn = !isInColumn },
            contentAlignment = Alignment.Center
        ) {
            if (isInColumn) {
                Column { items() }
            } else {
                Row { items() }
            }
        }
    }
}