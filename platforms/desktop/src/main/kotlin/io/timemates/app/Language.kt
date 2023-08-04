package io.timemates.app

import androidx.compose.runtime.Stable
import io.timemates.app.localization.EnglishStrings
import io.timemates.app.localization.Strings
import io.timemates.app.localization.UkrainianStrings
import java.util.Locale

/**
 * Retrieves localized strings based on the system locale.
 *
 * @return An instance of [Strings] representing the localized strings based on the system locale.
 * If the system locale is set to Ukrainian ("uk"), it returns [UkrainianStrings],
 * otherwise, it returns the default [EnglishStrings].
 */
@Stable
fun getStringsFromSystemLocale(): Strings = when (Locale.getDefault().language) {
    "uk" -> UkrainianStrings
    else -> EnglishStrings
}