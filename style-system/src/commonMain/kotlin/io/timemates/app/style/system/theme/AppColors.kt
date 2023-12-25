package io.timemates.app.style.system.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Represents a set of color values for various elements in the application's user interface.
 *
 * @property primary The primary color used for the main UI elements.
 * @property onPrimary The color used for text and icons on the primary color background.
 * @property secondary The secondary color used for secondary UI elements.
 * @property onSecondary The color used for text and icons on the secondary color background.
 * @property background The background color used for the overall application background.
 * @property onBackground The color used for text and icons on the background color.
 * @property surface The color used for surfaces, such as cards and dialogs.
 * @property surfaceVariant The variant color used for surfaces to create depth and hierarchy.
 */
data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val positive: Color
)

/**
 * Creates an instance of [AppColors] with specific color values for a light theme.
 *
 * @return An [AppColors] instance representing colors for a light theme.
 */
@Stable
fun lightColors(): AppColors {
    return AppColors(
        primary = Color(0xFF212121),
        onPrimary = Color(0xFFFFFFFF),
        secondary = Color(0xFFDEDEDE),
        onSecondary = Color(0xFF212121),
        background = Color(0xFFFFFFFF),
        onBackground = Color(0xFF212121),
        surface = Color(0xFFF5F5F5),
        surfaceVariant = Color(0xFFB1B1B1),
        secondaryVariant = Color(0xFFB1B1B1),
        positive = Color(0xFF54A457)
    )
}

/**
 * A [ProvidableCompositionLocal] for [AppColors].
 * The default value is set to [lightColors].
 */
val LocalAppColors: ProvidableCompositionLocal<AppColors> =
    staticCompositionLocalOf { lightColors() }
