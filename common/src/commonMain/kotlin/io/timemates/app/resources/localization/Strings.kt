package io.timemates.app.resources.localization

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import io.timemates.client.galaxia.GalaxiaContext

interface Strings {
    val appName: String get() = "TimeMates"

    val email: String
    val profile: String
    val timers: String
    val settings: String

    val createNew: String
    val joinExisting: String

    val restTime: String
    val workingTime: String
    val bigRest: String
    val startConfirmation: String
    val members: String

    val restTimeDesc: String
    val workingTimeDesc: String
    val bigRestDesc: String
    val startConfirmationDesc: String
    val membersDesc: String

    fun minutes(value: Int): String
}

val GalaxiaContext.strings: ProvidableCompositionLocal<Strings>
    get() = staticCompositionLocalOf { EnglishStrings }