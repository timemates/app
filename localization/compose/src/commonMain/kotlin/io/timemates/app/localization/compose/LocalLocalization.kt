package io.timemates.app.localization.compose

import androidx.compose.runtime.staticCompositionLocalOf
import io.timemates.app.localization.Strings
import io.timemates.app.localization.UkrainianStrings

val LocalStrings = staticCompositionLocalOf<Strings> { UkrainianStrings }