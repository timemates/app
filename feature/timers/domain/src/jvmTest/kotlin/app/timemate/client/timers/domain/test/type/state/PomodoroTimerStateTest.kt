@file:Suppress("detekt.LongMethod")
package app.timemate.client.timers.domain.test.type.state

import app.timemate.client.timers.domain.type.settings.PomodoroTimerSettings
import app.timemate.client.timers.domain.type.settings.value.PomodoroLongBreakPerShortBreaksCount
import app.timemate.client.timers.domain.type.settings.value.PomodoroConfirmationTimeoutTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroFocusTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroLongBreakTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroPreparationTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroShortBreakTime
import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCountSinceBreakReset
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class PomodoroTimerStateTest {
    // it's okay here to have a long list of parameters – it's a testing method
    @Suppress("detekt.LongParameterList", "SameParameterValue")
    private fun createSettings(
        isLongBreakEnabled: Boolean,
        longBreakPer: Int,
        focusTimeMinutes: Int,
        shortBreakDurationMinutes: Int,
        longBreakDurationMinutes: Int,
        requiresConfirmationBeforeStart: Boolean,
        isPreparationStateEnabled: Boolean,
        confirmationTimeoutMinutes: Int,
        preparationDurationMinutes: Int,
    ): PomodoroTimerSettings {
        return PomodoroTimerSettings(
            isLongBreakEnabled = isLongBreakEnabled,
            longBreakPer = PomodoroLongBreakPerShortBreaksCount.factory.createOrThrow(longBreakPer),
            pomodoroFocusTime = PomodoroFocusTime.factory.createOrThrow(focusTimeMinutes.minutes),
            pomodoroShortBreakTime = PomodoroShortBreakTime.factory.createOrThrow(shortBreakDurationMinutes.minutes),
            longBreakTime = PomodoroLongBreakTime.factory.createOrThrow(longBreakDurationMinutes.minutes),
            requiresConfirmationBeforeStart = requiresConfirmationBeforeStart,
            isPreparationStateEnabled = isPreparationStateEnabled,
            confirmationTimeoutTime = PomodoroConfirmationTimeoutTime.factory
                .createOrThrow(confirmationTimeoutMinutes.minutes),
            preparationTime = PomodoroPreparationTime.factory.createOrThrow(preparationDurationMinutes.minutes),
        )
    }

    // A simple ShortBreaksCountSinceBreakReset with a GIVEN count
    private fun shortBreakCountSinceReset(count: Int): PomodoroShortBreaksCountSinceBreakReset {
        return PomodoroShortBreaksCountSinceBreakReset.factory.createOrThrow(count)
    }

    @Test
    fun `Inactive start throws WHEN at is before startTime`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T10:00:00Z")
        val inactive = PomodoroTimerState.Inactive(startTime)
        val settings = createSettings(
            isLongBreakEnabled = true,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2
        )
        val invalidAt = startTime - 1.minutes

        // WHEN
        val exception = assertFailsWith<IllegalStateException> {
            inactive.start(invalidAt, settings)
        }

        // THEN
        assertContains(
            charSequence = exception.message!!,
            other = "New state cannot be past current",
        )
    }


    @Test
    fun `Inactive start returns Transition with updated Inactive and new Focus`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T11:00:00Z")
        val inactive = PomodoroTimerState.Inactive(startTime)
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 20,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2,
        )
        val at = startTime + 5.minutes

        // WHEN
        val transition = inactive.start(at, settings)

        // THEN
        assertEquals(
            expected = inactive.copy(endTime = at),
            actual = transition.updatedOldState,
        )
        assertEquals(
            expected = PomodoroTimerState.Focus(at, at + settings.pomodoroFocusTime.duration),
            actual = transition.nextState,
        )
    }

    @Test
    fun `Focus onExpiration returns ShortBreak WHEN long break disabled`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T12:00:00Z")
        val endTime = startTime + 25.minutes
        val focus = PomodoroTimerState.Focus(startTime, endTime)
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2
        )
        val shortBreaksCount = shortBreakCountSinceReset(0)

        // WHEN
        val next = focus.onExpiration(settings, shortBreaksCount)

        // THEN
        assertIs<PomodoroTimerState.ShortBreak>(next)
        assertEquals<Instant>(
            expected = focus.endTime,
            actual = next.startTime,
        )
        assertEquals<Instant>(
            expected = focus.endTime + settings.pomodoroShortBreakTime.duration,
            actual = next.endTime,
        )
    }

    @Test
    fun `Focus onExpiration returns ShortBreak WHEN shortBreaksCount less than threshold`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T13:00:00Z")
        val endTime = startTime + 25.minutes
        val focus = PomodoroTimerState.Focus(startTime, endTime)
        val settings = createSettings(
            isLongBreakEnabled = true,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2,
        )
        val shortBreaksCount = shortBreakCountSinceReset(1)

        // WHEN
        val next = focus.onExpiration(settings, shortBreaksCount)

        // THEN
        assertIs<PomodoroTimerState.ShortBreak>(next)
        assertEquals<Instant>(
            expected = focus.endTime,
            actual = next.startTime,
        )
        assertEquals<Instant>(
            expected = focus.endTime + settings.pomodoroShortBreakTime.duration,
            actual = next.endTime,
        )
    }

    @Test
    fun `Focus onExpiration returns LongBreak WHEN shortBreaksCount meets threshold`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T14:00:00Z")
        val endTime = startTime + 25.minutes
        val focus = PomodoroTimerState.Focus(startTime, endTime)
        val settings = createSettings(
            isLongBreakEnabled = true,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2,
        )
        val shortBreaksCount = shortBreakCountSinceReset(2)

        // WHEN
        val next = focus.onExpiration(settings, shortBreaksCount)

        // THEN
        assertIs<PomodoroTimerState.LongBreak>(next)
        assertEquals<Instant>(
            expected = focus.endTime,
            actual = next.startTime,
        )
        assertEquals<Instant>(
            expected = focus.endTime + settings.longBreakTime.duration,
            actual = next.endTime,
        )
    }

    @Test
    fun `Focus pause returns Transition with updated Focus and Paused`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T15:00:00Z")
        val endTime = startTime + 10.minutes
        val focus = PomodoroTimerState.Focus(startTime, endTime)
        val at = endTime - 2.minutes

        // WHEN
        val transition = focus.pause(at)

        // THEN
        assertEquals(
            expected = PomodoroTimerState.Focus(startTime, at),
            actual = transition.updatedOldState,
        )
        assertEquals(
            expected = PomodoroTimerState.Paused(at),
            actual = transition.nextState,
        )
    }

    @Test
    fun `Paused onExpiration returns Inactive with endTime equal to startTime plus default`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T16:00:00Z")
        val paused = PomodoroTimerState.Paused(startTime)

        // WHEN
        val next = paused.onExpiration()

        // THEN
        assertEquals(
            expected = paused.endTime,
            actual = startTime + 25.minutes,
        )
        assertEquals(
            expected = next.endTime,
            actual = Instant.DISTANT_FUTURE
        )
    }

    @Test
    fun `Paused resume throws WHEN at is before startTime`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T17:00:00Z")
        val paused = PomodoroTimerState.Paused(startTime)
        val invalidAt = startTime - 1.minutes
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 1,
            preparationDurationMinutes = 2
        )

        // WHEN + THEN
        val ex = assertFailsWith<IllegalStateException> {
            paused.resume(settings, invalidAt)
        }
        assertContains(ex.message!!, "New state cannot be past current")
    }

    @Test
    fun `Paused resume returns Transition to AwaitsConfirmation WHEN required`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T18:00:00Z")
        val paused = PomodoroTimerState.Paused(startTime)
        val at = startTime + 1.minutes
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = true,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3
        )

        // WHEN
        val transition = paused.resume(settings, at)

        // THEN
        assertEquals(expected = paused.startTime, actual = startTime)
        assertEquals(
            expected = PomodoroTimerState.Paused(startTime, at),
            actual = transition.updatedOldState,
        )
        assertIs<PomodoroTimerState.AwaitsConfirmation>(transition.nextState)

        val updatedOldState = transition.updatedOldState
        val nextState = transition.nextState
        assertEquals<Instant>(
            expected = updatedOldState.endTime,
            actual = nextState.startTime,
        )
        assertEquals<Instant>(
            expected = updatedOldState.endTime + settings.confirmationTimeoutTime.duration,
            actual = nextState.endTime,
        )
    }

    @Test
    fun `Paused resume returns Transition to Preparation WHEN not requiring confirmation but preparation enabled`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T19:00:00Z")
        val paused = PomodoroTimerState.Paused(startTime)
        val at = startTime + 1.minutes
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = true,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3,
        )

        // WHEN
        val transition = paused.resume(settings, at)

        // THEN
        assertEquals(expected = startTime, actual = paused.startTime)
        assertEquals(
            expected = PomodoroTimerState.Paused(startTime, at),
            actual = transition.updatedOldState,
        )
        assertIs<PomodoroTimerState.Preparation>(transition.nextState)

        val oldUpdatedState = transition.updatedOldState
        val nextState = transition.nextState
        assertEquals<Instant>(
            expected = oldUpdatedState.endTime,
            actual = nextState.startTime,
        )
        assertEquals<Instant>(
            expected = oldUpdatedState.endTime + settings.preparationTime.duration,
            actual = nextState.endTime,
        )
    }

    @Test
    fun `Paused resume returns Transition to Focus WHEN neither confirmation nor preparation required`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T20:00:00Z")
        val paused = PomodoroTimerState.Paused(startTime)
        val at = startTime + 1.minutes
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 30,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3,
        )

        // WHEN
        val transition = paused.resume(settings, at)

        // THEN
        assertEquals(
            expected = PomodoroTimerState.Paused(startTime, at),
            actual = transition.updatedOldState,
        )
        assertIs<PomodoroTimerState.Focus>(transition.nextState)

        val oldUpdatedState = transition.updatedOldState
        val nextState = transition.nextState
        assertEquals<Instant>(
            expected = oldUpdatedState.endTime,
            actual = nextState.startTime,
        )
        assertEquals<Instant>(
            expected = oldUpdatedState.endTime + settings.pomodoroFocusTime.duration,
            actual = nextState.endTime,
        )
    }

    @Test
    fun `ShortBreak onExpiration respects confirmation and preparation logic`() {
        // -- PomodoroTimerState.ShortBreak -> requiresConfirmationBeforeStart = true
        // GIVEN
        val startTime = Instant.parse("2025-06-03T21:00:00Z")
        val endTime = startTime + 5.minutes
        val shortBreak = PomodoroTimerState.ShortBreak(startTime, endTime)
        val settingsConfirm = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = true,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3
        )
        // WHEN
        val nextConfirm = shortBreak.onExpiration(settingsConfirm)
        // THEN
        assertIs<PomodoroTimerState.AwaitsConfirmation>(nextConfirm)
        assertEquals<Instant>(
            expected = shortBreak.endTime,
            actual = nextConfirm.startTime,
        )
        assertEquals<Instant>(
            expected = shortBreak.endTime + settingsConfirm.confirmationTimeoutTime.duration,
            actual = nextConfirm.endTime,
        )

        // -- PomodoroTimerState.ShortBreak -> isPreparationStateEnabled = true
        // GIVEN
        val settingsPrep = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = true,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 4
        )
        // WHEN
        val nextPrep = shortBreak.onExpiration(settingsPrep)
        // THEN
        assertIs<PomodoroTimerState.Preparation>(nextPrep)
        assertEquals<Instant>(shortBreak.endTime, nextPrep.startTime)
        assertEquals<Instant>(shortBreak.endTime + settingsPrep.preparationTime.duration, nextPrep.endTime)

        // -- PomodoroTimerState.ShortBreak -> default to Focus
        // GIVEN
        val settingsFocus = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 20,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 4
        )
        // WHEN
        val nextFocus = shortBreak.onExpiration(settingsFocus)
        // THEN
        assertIs<PomodoroTimerState.Focus>(nextFocus)
        assertEquals<Instant>(
            expected = shortBreak.endTime,
            actual = nextFocus.startTime,
        )
        assertEquals<Instant>(
            expected = shortBreak.endTime + settingsFocus.pomodoroFocusTime.duration,
            actual = nextFocus.endTime,
        )
    }

    @Test
    fun `ShortBreak terminate throws WHEN at is before startTime`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T22:00:00Z")
        val shortBreak = PomodoroTimerState.ShortBreak(startTime, startTime + 5.minutes)
        val invalidAt = startTime - 1.minutes
        // WHEN / THEN
        val ex = assertFailsWith<IllegalStateException> {
            shortBreak.terminate(invalidAt)
        }
        assertContains(ex.message!!, "New state cannot be past current")
    }

    @Test
    fun `ShortBreak terminate returns Transition with updated ShortBreak and Paused`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-03T23:00:00Z")
        val shortBreak = PomodoroTimerState.ShortBreak(startTime, startTime + 5.minutes)
        val at = startTime + 2.minutes
        // WHEN
        val transition = shortBreak.terminate(at)
        // THEN
        assertEquals(
            expected = PomodoroTimerState.ShortBreak(startTime, at),
            actual = transition.updatedOldState,
        )
        assertEquals(
            expected = PomodoroTimerState.Paused(at),
            actual = transition.nextState,
        )
    }

    @Test
    fun `LongBreak onExpiration respects confirmation and preparation logic`() {
        // -- PomodoroTimerState.LongBreak -> requiresConfirmationBeforeStart = true
        // GIVEN
        val startTime = Instant.parse("2025-06-04T00:00:00Z")
        val endTime = startTime + 15.minutes
        val longBreak = PomodoroTimerState.LongBreak(startTime, endTime)
        val settingsConfirm = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = true,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 4
        )
        // WHEN
        val nextConfirm = longBreak.onExpiration(settingsConfirm)
        // THEN
        assertIs<PomodoroTimerState.AwaitsConfirmation>(nextConfirm)
        assertEquals<Instant>(expected = longBreak.endTime, actual = nextConfirm.startTime)
        assertEquals<Instant>(
            expected = longBreak.endTime + settingsConfirm.confirmationTimeoutTime.duration,
            actual = nextConfirm.endTime,
        )

        // -- PomodoroTimerState.LongBreak -> isPreparationStateEnabled = true
        // GIVEN
        val settingsPrep = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 25,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = true,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3
        )
        // WHEN
        val nextPrep = longBreak.onExpiration(settingsPrep)
        // THEN
        assertIs<PomodoroTimerState.Preparation>(nextPrep)
        assertEquals<Instant>(
            expected = longBreak.endTime,
            actual = nextPrep.startTime,
        )
        assertEquals<Instant>(
            expected = longBreak.endTime + settingsPrep.preparationTime.duration,
            actual = nextPrep.endTime,
        )

        // -- PomodoroTimerState.LongBreak -> default to Focus
        // GIVEN
        val settingsFocus = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 30,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 3
        )
        // WHEN
        val nextFocus = longBreak.onExpiration(settingsFocus)
        // THEN
        assertIs<PomodoroTimerState.Focus>(nextFocus)
        assertEquals<Instant>(
            expected = longBreak.endTime,
            actual = nextFocus.startTime,
        )
        assertEquals<Instant>(
            expected = longBreak.endTime + settingsFocus.pomodoroFocusTime.duration,
            actual = nextFocus.endTime,
        )
    }

    @Test
    fun `LongBreak terminate throws WHEN at is before startTime`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-04T01:00:00Z")
        val longBreak = PomodoroTimerState.LongBreak(startTime, startTime + 15.minutes)
        val invalidAt = startTime - 1.minutes
        // WHEN / THEN
        val ex = assertFailsWith<IllegalStateException> {
            longBreak.terminate(invalidAt)
        }
        assertContains(ex.message!!, "New state cannot be past current")
    }

    @Test
    fun `LongBreak terminate returns Transition with updated LongBreak and Paused`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-04T02:00:00Z")
        val longBreak = PomodoroTimerState.LongBreak(startTime, startTime + 15.minutes)
        val at = startTime + 7.minutes
        // WHEN
        val transition = longBreak.terminate(at)
        // THEN
        assertEquals(PomodoroTimerState.LongBreak(startTime, at), transition.updatedOldState)
        assertEquals(PomodoroTimerState.Paused(at), transition.nextState)
    }

    @Test
    fun `Preparation onExpiration returns Focus`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-04T03:00:00Z")
        val duration = 3.minutes
        val preparation = PomodoroTimerState.Preparation(startTime, startTime + duration)
        val settings = createSettings(
            isLongBreakEnabled = false,
            longBreakPer = 2,
            focusTimeMinutes = 20,
            shortBreakDurationMinutes = 5,
            longBreakDurationMinutes = 15,
            requiresConfirmationBeforeStart = false,
            isPreparationStateEnabled = false,
            confirmationTimeoutMinutes = 2,
            preparationDurationMinutes = 4
        )
        // WHEN
        val next = preparation.onExpiration(settings)
        // THEN
        assertIs<PomodoroTimerState.Focus>(next)
        assertEquals<Instant>(
            expected = preparation.endTime,
            actual = next.startTime,
        )
        assertEquals<Instant>(
            expected = preparation.endTime + settings.pomodoroFocusTime.duration,
            actual = next.endTime,
        )
    }

    @Test
    fun `Preparation pause throws WHEN at is before startTime`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-04T04:00:00Z")
        val preparation = PomodoroTimerState.Preparation(startTime, startTime + 4.minutes)
        val invalidAt = startTime - 1.minutes
        // WHEN / THEN
        val ex = assertFailsWith<IllegalStateException> {
            preparation.pause(invalidAt)
        }
        assertContains(ex.message!!, "New state cannot be past current")
    }

    @Test
    fun `Preparation pause returns Transition with updated Preparation and Paused`() {
        // GIVEN
        val startTime = Instant.parse("2025-06-04T05:00:00Z")
        val preparation = PomodoroTimerState.Preparation(startTime, startTime + 3.minutes)
        val at = startTime + 2.minutes
        // WHEN
        val transition = preparation.pause(at)
        // THEN
        assertEquals(
            expected = PomodoroTimerState.Preparation(startTime, at),
            actual = transition.updatedOldState,
        )
        assertEquals(
            expected = PomodoroTimerState.Paused(at),
            actual = transition.nextState,
        )
    }
}
