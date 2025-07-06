package app.timemate.client.timers.domain.test.type

import app.timemate.client.timers.domain.type.PomodoroTimer
import app.timemate.client.timers.domain.type.settings.PomodoroTimerSettings
import app.timemate.client.timers.domain.type.settings.value.PomodoroLongBreakPerShortBreaksCount
import app.timemate.client.timers.domain.type.settings.value.PomodoroConfirmationTimeoutTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroFocusTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroLongBreakTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroPreparationTime
import app.timemate.client.timers.domain.type.settings.value.PomodoroShortBreakTime
import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import app.timemate.client.timers.domain.type.task.LinkedTimerTask
import app.timemate.client.timers.domain.type.task.value.LinkedTaskId
import app.timemate.client.timers.domain.type.task.value.LinkedTaskName
import app.timemate.client.timers.domain.type.value.TimerId
import app.timemate.client.timers.domain.type.value.TimerName
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

class PomodoroTimerTest {
    private val initialTime = Instant.fromEpochMilliseconds(0)
    private val laterTime = Instant.fromEpochMilliseconds(1_000)

    @Test
    fun `transition applies the given state transition`() {
        // GIVEN
        val state = PomodoroTimerState.Inactive(initialTime)
        val settings = defaultSettings()
        val timer = createTimer(state, settings)
        val newState = state.start(laterTime, settings).nextState

        // WHEN
        val transitioned = timer.transition { newState }

        // THEN
        assertEquals(newState, transitioned.state)
    }

    @Test
    fun `linkTask correctly updates the linked task`() {
        // GIVEN
        val timer = createTimer()
        val task = createTask()

        // WHEN
        val updated = timer.linkTask(task)

        // THEN
        assertEquals(task, updated.linkedTask)
    }

    @Test
    fun `unlinkTask removes the task if linked`() {
        // GIVEN
        val task = createTask()
        val timer = createTimer().linkTask(task)

        // WHEN
        val updated = timer.unlinkTask()

        // THEN
        assertNull(updated.linkedTask)
    }

    @Test
    fun `unlinkTask throws if no task linked`() {
        // GIVEN
        val timer = createTimer()

        // WHEN / THEN
        assertFailsWith<IllegalArgumentException> {
            timer.unlinkTask()
        }
    }

    private fun createTimer(
        state: PomodoroTimerState = PomodoroTimerState.Inactive(initialTime),
        settings: PomodoroTimerSettings = defaultSettings(),
    ): PomodoroTimer {
        return PomodoroTimer(
            id = TimerId.factory.createOrThrow(1),
            name = TimerName.factory.createOrThrow("Test Timer"),
            creationTime = initialTime,
            state = state,
            linkedTask = null,
            settings = settings,
        )
    }

    private fun createTask(): LinkedTimerTask {
        return LinkedTimerTask(
            id = LinkedTaskId.factory.createOrThrow(1),
            name = LinkedTaskName.factory.createOrThrow("Test Task"),
            creationTime = initialTime,
            dueTime = initialTime + 30.minutes,
        )
    }

    private fun defaultSettings(): PomodoroTimerSettings = PomodoroTimerSettings(
        pomodoroFocusTime = PomodoroFocusTime.factory.createOrThrow(25.minutes),
        pomodoroShortBreakTime = PomodoroShortBreakTime.factory.createOrThrow(5.minutes),
        longBreakTime = PomodoroLongBreakTime.factory.createOrThrow(10.minutes),
        longBreakPer = PomodoroLongBreakPerShortBreaksCount.DEFAULT,
        isLongBreakEnabled = true,
        isPreparationStateEnabled = false,
        preparationTime = PomodoroPreparationTime.factory.createOrThrow(10.seconds),
        requiresConfirmationBeforeStart = false,
        confirmationTimeoutTime = PomodoroConfirmationTimeoutTime.factory.createOrThrow(30.seconds),
    )
}