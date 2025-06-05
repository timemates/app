package app.timemate.client.timers.domain.type.settings

import app.timemate.client.timers.domain.type.settings.value.*
import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCount
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class PomodoroTimerSettings(
    val pomodoroFocusTime: PomodoroFocusTime = PomodoroFocusTime.factory.createOrThrow(25.minutes),

    val pomodoroShortBreakTime: PomodoroShortBreakTime = PomodoroShortBreakTime.factory.createOrThrow(5.minutes),
    val longBreakTime: PomodoroLongBreakTime = PomodoroLongBreakTime.factory.createOrThrow(10.minutes),
    val longBreakPer: PomodoroShortBreaksCount = PomodoroShortBreaksCount.factory.createOrThrow(4),
    val isLongBreakEnabled: Boolean = true,

    val isPreparationStateEnabled: Boolean = false,
    val preparationTime: PomodoroPreparationTime = PomodoroPreparationTime.factory.createOrThrow(10.seconds),

    val requiresConfirmationBeforeStart: Boolean = false,
    val confirmationTimeoutTime: PomodoroConfirmationTimeoutTime = PomodoroConfirmationTimeoutTime.factory.createOrThrow(30.seconds),
)