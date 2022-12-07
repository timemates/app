package com.y9vad9.pomodoro.client.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

interface Localization {
    val appName: String get() = "Pomodoro"
    val authorization get() = "Authorization"
    val signInViaGoogle get() = "Sign in via Google"
    val localTimers get() = "Local timers"
    val onlineTimers get() = "Online timers"
    val profile get() = "Profile"
    val timerNotUsed get() = "Timer wasn't used yet."
    val noTimers get() = "There is no timers yet."
}

open class EnglishLocalization : Localization

val Strings: ProvidableCompositionLocal<Localization> =
    staticCompositionLocalOf { EnglishLocalization() }

val CurrentStrings @Composable get() = Strings.current