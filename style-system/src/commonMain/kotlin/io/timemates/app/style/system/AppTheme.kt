package io.timemates.app.style.system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import io.timemates.app.style.system.resources.*

object AppTheme {
    /**
     * Returns the current [TimeMatesColors] instance for the current theme.
     */
    val colors: TimeMatesColors
        @ReadOnlyComposable
        @Composable
        get() = LocalColors.current

    /**
     * Returns the current [TimeMatesSizes] instance for the current theme.
     */
    val sizes: TimeMatesSizes
        @ReadOnlyComposable
        @Composable
        get() = LocalSizes.current

    /**
     * Returns the current [TimeMatesShapes] instance for the current theme.
     */
    val shapes: TimeMatesShapes
        @ReadOnlyComposable
        @Composable
        get() = LocalShapes.current

    /**
     * Returns the current [TimeMatesTypography] instance for the current theme.
     */
    val typography: TimeMatesTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current

    /**
     * Returns the current [TimeMatesAlpha] instance for the current theme.
     */
    val alpha: TimeMatesAlpha
        @ReadOnlyComposable
        @Composable
        get() = LocalAlpha.current
}

/**
 * A Composable function that provides the Galaxia theme to its content.
 *
 * @param content The content to wrap with the Galaxia theme.
 */
@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val sizes = timeMatesSizes()
    val shapes = timeMatesShapes(sizes)

    val colors = if (darkTheme) darkColors() else lightColors()

    CompositionLocalProvider(
        values = arrayOf(
            LocalSizes provides sizes,
            LocalColors provides colors,
            LocalShapes provides shapes,
            LocalAlpha provides TimeMatesAlpha(),
            LocalTypography provides timeMatesTypography()
        ),
        content = content
    )
}