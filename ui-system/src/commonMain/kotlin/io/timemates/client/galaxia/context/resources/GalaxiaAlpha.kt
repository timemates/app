package io.timemates.client.galaxia.context.resources

import androidx.compose.runtime.*

@Immutable
data class GalaxiaAlpha(
    val enabled: Float = 1f,
    val disabled: Float = .3f,
)

/**
 * Sets alpha for item that can be disabled.
 */
@Stable
fun GalaxiaAlpha.withDisability(itemEnabled: Boolean): Float {
    return when (itemEnabled) {
        true -> this.enabled
        false -> disabled
    }
}

val LocalAlpha: ProvidableCompositionLocal<GalaxiaAlpha> = staticCompositionLocalOf { GalaxiaAlpha() }