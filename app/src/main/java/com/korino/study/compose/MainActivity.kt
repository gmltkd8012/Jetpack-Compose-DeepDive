package com.korino.study.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ApproachLayoutModifierNode
import androidx.compose.ui.layout.ApproachMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Dialog
import com.korino.study.compose.lookahead.LookaheadExampleScreen
import com.korino.study.compose.ui.theme.JetpackComposeDeepDiveTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LookaheadExampleScreen()
        }
    }

//    @TraceRecomposition
//    @Composable
//    fun TransitionBox(
//        isExpanded: Boolean = false,
//        onClick: () -> Unit
//    ) {
//        val size by animateSizeAsState(
//            targetValue = if (isExpanded) Size(300F, 300F) else Size(100F, 100F),
//            animationSpec = tween(1000)
//        )
//
//        Box(
//            modifier = Modifier
//                .layout { measurable, _ ->
//                    val sizeInt = size.width.toInt()
//                    val placeable = measurable.measure(
//                        Constraints.fixed(sizeInt, sizeInt)
//                    )
//                    layout(sizeInt, sizeInt) {
//                        placeable.place(0, 0)
//                    }
//                }
//                .background(Color.Blue)
//                .clickable { onClick() }
//        )
//
//    }
//
//    @TraceRecomposition
//    @Composable
//    fun SmoothTransitionBox(
//        isExpanded: Boolean= false,
//        onClick: () -> Unit
//    ) {
//        var sizeAnimation = remember {
//            Animatable(IntSize(100, 100), IntSize.VectorConverter)
//        }
//
//        LaunchedEffect(isExpanded) {
//            // Step 1: 목표 크기 결정
//            val targetSize = IntSize(
//                width = if (isExpanded) 300 else 100,
//                height = if (isExpanded) 300 else 100
//            )
//
//            sizeAnimation.animateTo(targetSize)
//            Log.d("heesang", "SmoothTransitionBox: ${sizeAnimation.value}")
//        }
//
//        LookaheadScope {
//            Box(
//                modifier = Modifier
//                    .background(Color.Blue)
//                    .clickable { onClick() }
//                    .approachLayout(
//                        isMeasurementApproachInProgress = { lookaheadSize ->
//                            sizeAnimation.value != lookaheadSize
//                        },
//                        approachMeasure = { measurable, _ ->
//                            val (width, height) = sizeAnimation.value
//                            val placeable = measurable.measure(
//                                Constraints.fixed(width, height)
//                            )
//
//                            layout(width, height) {
//                                placeable.place(0, 0)
//                            }
//                        }
//                    )
//            )
//        }
//    }
}
