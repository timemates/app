package io.timemates.app.style.system.resources

import androidx.compose.runtime.*

@Immutable
data class TimeMatesAlpha(
    val enabled: Float = 1f,
    val disabled: Float = .3f,
)

/**
 * Sets alpha for item that can be disabled.
 */
@Stable
fun TimeMatesAlpha.alphaForEnabled(itemEnabled: Boolean): Float {
    return when (itemEnabled) {
        true -> this.enabled
        false -> disabled
    }
}

val LocalAlpha: ProvidableCompositionLocal<TimeMatesAlpha> = staticCompositionLocalOf {
    TimeMatesAlpha()
}