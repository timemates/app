package org.timemates.app.localization.compose

import androidx.compose.runtime.staticCompositionLocalOf
import org.timemates.app.localization.EnglishStrings
import org.timemates.app.localization.Strings
import org.timemates.app.localization.UkrainianStrings

val LocalStrings = staticCompositionLocalOf<Strings> { EnglishStrings }