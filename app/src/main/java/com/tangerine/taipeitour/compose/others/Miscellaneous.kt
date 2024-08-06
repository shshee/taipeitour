package com.tangerine.taipeitour.compose.others

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.tangerine.core.source.R

@Composable
fun myPadding() = dimensionResource(id = R.dimen.standard_compose_padding)

@Composable
fun createShape(cornerRadius: Dp): Shape {

    val density = LocalDensity.current
    return GenericShape { size, _ ->

        val width = size.width
        val height = size.height
        val cornerRadiusPx = density.run { cornerRadius.toPx() }

        moveTo(0f, height)

        // Vertical line on left size
        lineTo(0f, cornerRadiusPx * 2)

        arcTo(
            rect = Rect(
                offset = Offset.Zero,
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(width - cornerRadiusPx * 2, 0f)
        arcTo(
            rect = Rect(
                offset = Offset(width - cornerRadiusPx * 2, 0f),
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2)
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        // Vertical line on right size
        lineTo(width, height)

    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}