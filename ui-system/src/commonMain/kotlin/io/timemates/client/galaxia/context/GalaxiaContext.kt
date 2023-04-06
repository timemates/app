package io.timemates.client.galaxia.context

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import io.timemates.client.galaxia.context.resources.*

/**
 * This object provides access to the current theme colors, sizes, and shapes through a Composable interface.
 * It acts as a central access point for theme-related values that can be used throughout an app.
 */
object GalaxiaContext {
    /**
     * Returns the current [GalaxiaColors] instance for the current theme.
     */
    val colors: GalaxiaColors @Composable get() = LocalColors.current

    /**
     * Returns the current [GalaxiaSizes] instance for the current theme.
     */
    val sizes: GalaxiaSizes @Composable get() = LocalSizes.current

    /**
     * Returns the current [GalaxiaShapes] instance for the current theme.
     */
    val shapes: GalaxiaShapes @Composable get() = LocalShapes.current

    /**
     * Returns the current [GalaxiaTypography] instance for the current theme.
     */
    val typography: GalaxiaTypography @Composable get() = LocalTypography.current

    /**
     * Returns the current [GalaxiaAlpha] instance for the current theme.
     */
    val alpha: GalaxiaAlpha @Composable get() = LocalAlpha.current
}

/**
 * A Composable function that provides the Galaxia theme to its content.
 *
 * @param content The content to wrap with the Galaxia theme.
 */
@Composable
fun GalaxiaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val sizes = galaxiaSizes()
    val shapes = galaxiaShapes(sizes)

    val colors = if (darkTheme) darkGalaxiaColors() else lightGalaxiaColors()

    CompositionLocalProvider(
        values = arrayOf(
            LocalSizes provides sizes,
            LocalColors provides colors,
            LocalShapes provides shapes,
            LocalAlpha provides GalaxiaAlpha(),
            LocalTypography provides galaxiaTypography()
        ),
        content = content
    )
}