package io.timemates.app.style.system.resources

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TimeMatesColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val onBackground: Color,
)

fun lightColors(): TimeMatesColors {
    return TimeMatesColors(
        primary = Color(0x212121),
        onPrimary = Color.White,
        secondary = Color(0xDEDEDE),
        onSecondary = Color(0x212121),
        background = Color.White,
        onBackground = Color(0x212121),
        secondaryVariant = Color(0xB1B1B1),
    )
}

fun darkColors(): TimeMatesColors {
    return TimeMatesColors(
        primary = Color(0xDEDEDE),
        onPrimary = Color(0x212121),
        secondary = Color(0x212121),
        onSecondary = Color.White,
        background = Color(0x212121),
        onBackground = Color.White,
        secondaryVariant = Color(0x707070),
    )
}

val LocalColors: ProvidableCompositionLocal<TimeMatesColors> = staticCompositionLocalOf {
    lightColors()
}