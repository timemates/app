package org.timemates.app.feature.common.providable

import androidx.compose.runtime.staticCompositionLocalOf
import org.timemates.app.foundation.time.TimeProvider

val LocalTimeProvider = staticCompositionLocalOf<TimeProvider> {
    error("TimeProvider was not provided.")
}