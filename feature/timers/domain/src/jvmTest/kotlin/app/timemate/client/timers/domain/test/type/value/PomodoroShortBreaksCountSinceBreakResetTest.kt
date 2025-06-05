package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCountSinceBreakReset
import kotlin.test.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class PomodoroShortBreaksCountSinceBreakResetTest {

    // Helper constructors for different state types
    private fun focus(start: Instant, durationMinutes: Int) =
        PomodoroTimerState.Focus(start, start + durationMinutes.minutes)

    private fun shortBreak(start: Instant, durationMinutes: Int) =
        PomodoroTimerState.ShortBreak(start, start + durationMinutes.minutes)

    private fun paused(start: Instant, durationMinutes: Int = 5) =
        PomodoroTimerState.Paused(start, start + durationMinutes.minutes)

    private fun preparation(start: Instant, durationMinutes: Int) =
        PomodoroTimerState.Preparation(start, start + durationMinutes.minutes)

    private fun awaitsConfirmation(start: Instant, durationMinutes: Int) =
        PomodoroTimerState.AwaitsConfirmation(start, start + durationMinutes.minutes)

    private fun longBreak(start: Instant, durationMinutes: Int) =
        PomodoroTimerState.LongBreak(start, start + durationMinutes.minutes)

    private fun inactive(at: Instant) =
        PomodoroTimerState.Inactive(at)

    @Test
    fun `from returns zero when list is empty`() {
        // GIVEN
        val emptyStates = emptyList<PomodoroTimerState>()

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.from(emptyStates)

        // THEN
        assertEquals(0, result.int)
    }

    @Test
    fun `from returns zero when streak contains no short breaks`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T10:00:00Z")
        val states = listOf(
            focus(base, 25),
            focus(base + 25.minutes, 25),
            paused(base + 50.minutes, 5),
            preparation(base + 55.minutes, 2),
            awaitsConfirmation(base + 57.minutes, 3)
        )
        // WHEN
        // -- Validate continuity
        val continuous = states.zipWithNext().all { (curr, next) ->
            curr.endTime == next.startTime
        }
        // THEN
        assertTrue(continuous)

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.from(states)

        // THEN
        assertEquals(0, result.int)
    }

    @Test
    fun `from counts only short breaks in mixed valid streak`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T08:00:00Z")
        val states = listOf(
            shortBreak(base, 5),
            focus(base + 5.minutes, 25),
            shortBreak(base + 30.minutes, 5),
            paused(base + 35.minutes, 5),
            shortBreak(base + 40.minutes, 5),
            preparation(base + 45.minutes, 2),
            shortBreak(base + 47.minutes, 5)
        )
        // WHEN
        // -- Validate continuity
        val isContinuous = states.zipWithNext().all { (c, n) -> c.endTime == n.startTime }

        // THEN
        assertTrue(isContinuous)

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.from(states)

        // THEN
        assertEquals(4, result.int)
    }

    @Test
    fun `from allows maximum valid number of entries`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T09:00:00Z")
        val states = mutableListOf<PomodoroTimerState>()
        var current = base
        repeat(PomodoroShortBreaksCountSinceBreakReset.MAX_ENTRIES) {
            states += shortBreak(current, 5)
            current += 5.minutes
        }
        // WHEN
        // -- Validate continuity
        val isContinuous = states.zipWithNext().all { (c, n) -> c.endTime == n.startTime }

        // THEN
        assertTrue(isContinuous)

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.from(states)

        // THEN
        assertEquals(PomodoroShortBreaksCountSinceBreakReset.MAX_ENTRIES, result.int)
    }

    @Test
    fun `from throws when list size equals or exceeds MAX_ENTRIES`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T11:00:00Z")
        val states = mutableListOf<PomodoroTimerState>()
        var current = base
        // -- Create MAX_ENTRIES + 1 entries
        repeat(PomodoroShortBreaksCountSinceBreakReset.MAX_ENTRIES + 1) {
            states += shortBreak(current, 1)
            current += 1.minutes
        }

        // WHEN
        val ex = assertFailsWith<IllegalArgumentException> {
            PomodoroShortBreaksCountSinceBreakReset.from(states)
        }

        // THEN
        assertTrue("we don't load huge lists" in ex.message!!)
    }

    @Test
    fun `from throws when any state is LongBreak`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T12:00:00Z")
        val states = listOf(
            shortBreak(base, 5),
            longBreak(base + 5.minutes, 15),
            shortBreak(base + 20.minutes, 5)
        )
        // WHEN
        // -- Validate continuity
        val isContinuous = states.zipWithNext().all { (c, n) -> c.endTime == n.startTime }

        // THEN
        assertTrue(isContinuous)

        // WHEN
        val ex = assertFailsWith<IllegalArgumentException> {
            PomodoroShortBreaksCountSinceBreakReset.from(states)
        }

        // THEN
        assertTrue("list shouldn't include long breaks" in ex.message!!)
    }

    @Test
    fun `from throws when any state is Inactive`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T13:00:00Z")
        val states = listOf(
            shortBreak(base, 5),
            inactive(base + 5.minutes),
            shortBreak(base + 10.minutes, 5)
        )

        // WHEN
        val ex = assertFailsWith<IllegalArgumentException> {
            PomodoroShortBreaksCountSinceBreakReset.from(states)
        }

        // THEN
        assertTrue("list shouldn't include long breaks or inactive states" in ex.message!!)
    }

    @Test
    fun `from throws when states are not time-continuous`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T14:00:00Z")
        val state1 = shortBreak(base, 5)
        val state2 = shortBreak(base + 6.minutes, 5) // gap of 1 minute

        // THEN
        assertNotEquals(state1.endTime, state2.startTime)

        // WHEN
        val ex = assertFailsWith<IllegalArgumentException> {
            PomodoroShortBreaksCountSinceBreakReset.from(listOf(state1, state2))
        }

        // THEN
        assertTrue("states must be continuous in time" in ex.message!!)
    }

    @Test
    fun `from throws when order of states is reversed`() {
        // GIVEN
        val base = Instant.parse("2025-06-03T15:00:00Z")
        val state1 = shortBreak(base, 5)
        val state2 = shortBreak(base + 5.minutes, 5)

        // WHEN
        // -- Reverse the list -> continuity broken
        val ex = assertFailsWith<IllegalArgumentException> {
            PomodoroShortBreaksCountSinceBreakReset.from(listOf(state2, state1))
        }

        // THEN
        assertContains(ex.message!!, "states must be continuous in time")
    }
}
