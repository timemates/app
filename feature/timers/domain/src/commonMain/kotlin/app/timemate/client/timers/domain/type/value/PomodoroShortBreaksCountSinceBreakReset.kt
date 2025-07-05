package app.timemate.client.timers.domain.type.value

import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import kotlin.jvm.JvmInline


/**
 * Represents the count of short breaks that have occurred since the most recent reset point
 * in the Pomodoro timer cycle.
 *
 * A "reset point" is defined as the latest occurrence of either a [PomodoroTimerState.LongBreak]
 * or a [PomodoroTimerState.Inactive] state, after which the current streak of short breaks
 * is tracked.
 *
 * This value class encapsulates the number of consecutive short breaks taken since that reset,
 * enforcing an upper bound on the streak size to avoid excessive memory or performance costs.
 *
 * @property int The number of short breaks since the last reset. Guaranteed to be less than or equal to [MAX_ENTRIES].
 */
@JvmInline
value class PomodoroShortBreaksCountSinceBreakReset private constructor(
    val int: Int,
) {
    companion object Companion {
        val factory: ValueFactory<PomodoroShortBreaksCountSinceBreakReset, Int> = factory(
            rules = listOf(MinValueValidationRule(0)),
            constructor = ::PomodoroShortBreaksCountSinceBreakReset,
        )
    }
}
