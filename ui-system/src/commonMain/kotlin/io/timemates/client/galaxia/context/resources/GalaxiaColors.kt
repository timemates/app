package io.timemates.client.galaxia.context.resources

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Immutable
data class GalaxiaColors(
    val figureBase: Color,
    val figureSecondary: Color,
    val figureDisabled: Color,
    val figurePositive: Color,
    val figureInverted: Color,
    val figurePause: Color,
    val figureFocus: Color,
    val bgHigh: Color,
    val bgHighest: Color,
    val bgBase: Color,
    val bgAccent: Color,
    val rippleOnBackground: Color,
)

@Stable
fun lightGalaxiaColors(): GalaxiaColors = GalaxiaColors(
    figureBase = Color(0xFF0E0E0E),
    figureInverted = Color.White,
    figureSecondary = Color(0xFF625B71),
    figureDisabled = Color(0xFF4A4458),
    figurePositive = Color(0xFF54A457),
    figurePause = Color(0xFF0047FF),
    figureFocus = Color(0xFFBF7E01),
    bgBase = Color.White,
    bgHigh = Color(0xFFD9D9D9),
    bgHighest = Color(0xFFD9D9D9),
    bgAccent = Color(0xFF0E0E0E),
    rippleOnBackground = Color.Gray,
)

@Stable
fun darkGalaxiaColors(): GalaxiaColors = GalaxiaColors(
    figureBase = Color(0xFF212121),
    figureInverted = Color(0xFF0E0E0E),
    figureSecondary = Color(0xFFB3B0B8),
    figureDisabled = Color(0xFF7E7A87),
    figurePositive = Color(0xFF8BC34A),
    figurePause = Color(0xFF2196F3),
    figureFocus = Color(0xFFFFA726),
    bgBase = Color(0xFF121212),
    bgHigh = Color(0xFF424242),
    bgHighest = Color(0xFF616161),
    bgAccent = Color(0xFFFFFFFF),
    rippleOnBackground = Color.Gray,
)

val LocalColors: ProvidableCompositionLocal<GalaxiaColors> = staticCompositionLocalOf {
    lightGalaxiaColors()
}