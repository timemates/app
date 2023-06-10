package io.timemates.app.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import io.timemates.app.core.localization.EnglishStrings
import io.timemates.app.core.localization.Strings

val LocalStringsProvider = staticCompositionLocalOf<Strings> { EnglishStrings }

val LocalStrings @Composable get() = LocalStringsProvider.current