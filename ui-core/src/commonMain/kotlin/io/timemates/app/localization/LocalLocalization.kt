package io.timemates.app.localization

import androidx.compose.runtime.staticCompositionLocalOf
import io.timemates.app.core.localization.EnglishStrings
import io.timemates.app.core.localization.Strings

val LocalStrings = staticCompositionLocalOf<Strings> { EnglishStrings }