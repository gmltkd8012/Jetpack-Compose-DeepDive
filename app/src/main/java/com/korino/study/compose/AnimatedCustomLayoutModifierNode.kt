package com.korino.study.compose

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.core.VectorConverter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ApproachLayoutModifierNode
import androidx.compose.ui.layout.ApproachMeasureScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round

@OptIn(ExperimentalAnimatableApi::class)
class AnimatedCustomLayoutModifierNode(
    var lookaheadScope: LookaheadScope,
) : ApproachLayoutModifierNode, Modifier.Node() {

    private val offsetAnimation: DeferredTargetAnimation<IntOffset, AnimationVector2D> =
        DeferredTargetAnimation(IntOffset.VectorConverter)

    override fun isMeasurementApproachInProgress(lookaheadSize: IntSize): Boolean {
        return false
    }

    override fun Placeable.PlacementScope.isPlacementApproachInProgress(
        lookaheadCoordinates: LayoutCoordinates
    ): Boolean {
        val target: IntOffset = with(lookaheadScope) {
            lookaheadCoordinates
                .localLookaheadPositionOf(lookaheadCoordinates)
                .round()
        }

        offsetAnimation.updateTarget(target, coroutineScope)
        return !offsetAnimation.isIdle
    }

    override fun ApproachMeasureScope.approachMeasure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            val coordinates = coordinates
            if (coordinates != null) {
                val target = with(lookaheadScope) {
                    lookaheadScopeCoordinates
                        .localLookaheadPositionOf(coordinates)
                        .round()
                }
                val animatedOffset = offsetAnimation.updateTarget(target, coroutineScope)
                val placementOffset = with(lookaheadScope) {
                    lookaheadScopeCoordinates
                        .localPositionOf(coordinates, Offset.Zero)
                        .round()
                }
                val (x, y) = animatedOffset - placementOffset
                placeable.place(x, y)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}

@OptIn(ExperimentalAnimatableApi::class)
data class AnimatePlacementNodeElement(
    val lookaheadScope: LookaheadScope
) : ModifierNodeElement<AnimatedCustomLayoutModifierNode>() {

    override fun create(): AnimatedCustomLayoutModifierNode {
        return AnimatedCustomLayoutModifierNode(lookaheadScope)
    }

    override fun update(node: AnimatedCustomLayoutModifierNode) {
        node.lookaheadScope = lookaheadScope
    }
}