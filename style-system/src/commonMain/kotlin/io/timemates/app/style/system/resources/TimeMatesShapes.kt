package io.timemates.app.style.system.resources

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

data class TimeMatesShapes(
    val circle: Shape,
    val cornerXs: Shape,
    val cornerXxs: Shape,
)

@Stable
@Composable
fun timeMatesShapes(sizes: TimeMatesSizes): TimeMatesShapes = TimeMatesShapes(
    circle = CircleShape,
    cornerXs = RoundedCornerShape(sizes.xs),
    cornerXxs = RoundedCornerShape(sizes.xxs),
)

val LocalShapes: ProvidableCompositionLocal<TimeMatesShapes> =
    staticCompositionLocalOf { error("Should be provided") }