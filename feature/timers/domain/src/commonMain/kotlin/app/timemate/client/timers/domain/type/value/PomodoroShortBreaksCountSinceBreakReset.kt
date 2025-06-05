package app.timemate.client.timers.domain.type.value

import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCountSinceBreakReset.Companion.MAX_ENTRIES
import kotlin.jvm.Throws


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
        /**
         * The maximum number of states allowed in the streak list used to create this instance.
         * This limit prevents loading excessively large history sequences.
         */
        const val MAX_ENTRIES: Int = 30

        /**
         * Constructs a [PomodoroShortBreaksCountSinceBreakReset] from a chronological list of
         * [PomodoroTimerState] instances representing the timer states since the last reset.
         *
         * The input list must satisfy the following constraints:
         * - It contains fewer than [MAX_ENTRIES] elements.
         * - It excludes any [PomodoroTimerState.LongBreak] or [PomodoroTimerState.Inactive] states,
         *   since these represent reset points and should not be part of the current streak.
         * - The states are continuous in time without gaps or overlaps:
         *   each state's [PomodoroTimerState.startTime] timestamp must exactly match the [PomodoroTimerState.endTime] timestamp of the previous state.
         *
         * If these conditions are not met, an [IllegalArgumentException] will be thrown.
         *
         * @param streak A list of consecutive pomodoro timer states since the last reset point,
         *               ordered chronologically from earliest to latest.
         * @return A new [PomodoroShortBreaksCountSinceBreakReset] instance containing the count of
         *         short breaks within the provided streak.
         */
        fun from(streak: List<PomodoroTimerState>): PomodoroShortBreaksCountSinceBreakReset {
            require(streak.size <= MAX_ENTRIES) {
                "Contract is violated: we don't load huge lists of streak."
            }

            require(
                !streak.any {
                    it is PomodoroTimerState.LongBreak || it is PomodoroTimerState.Inactive
                }
            ) {
                "Contract is violated: list shouldn't include long breaks or inactive states."
            }

            streak.zipWithNext().forEach { (current, next) ->
                require(current.endTime == next.startTime) {
                    "Contract is violated: states must be continuous in time. Found gap or overlap between $current and $next."
                }
            }

            return PomodoroShortBreaksCountSinceBreakReset(streak.count { it is PomodoroTimerState.ShortBreak })
        }
    }
}
