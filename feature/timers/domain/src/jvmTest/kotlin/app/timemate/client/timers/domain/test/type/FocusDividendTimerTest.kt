package app.timemate.client.timers.domain.test.type

import app.timemate.client.timers.domain.type.FocusDividendTimer
import app.timemate.client.timers.domain.type.state.FocusDividendTimerState
import app.timemate.client.timers.domain.type.task.LinkedTimerTask
import app.timemate.client.timers.domain.type.task.value.LinkedTaskId
import app.timemate.client.timers.domain.type.task.value.LinkedTaskName
import app.timemate.client.timers.domain.type.value.TimerId
import app.timemate.client.timers.domain.type.value.TimerName
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class FocusDividendTimerTest {
    private val initialTime = Instant.fromEpochMilliseconds(0)
    private val laterTime = Instant.fromEpochMilliseconds(5_000)

    @Test
    fun `transition updates state correctly`() {
        // GIVEN
        val initialState = FocusDividendTimerState.Earning(startTime = initialTime, endTime = null)
        val timer = createTimerWithState(initialState)

        // WHEN
        val transition = initialState.spend(laterTime)
        val transitionedTimer = timer.transition { transition.nextState }

        // THEN
        assertEquals(transition.nextState, transitionedTimer.state)
    }

    @Test
    fun `linkTask sets the linked task correctly`() {
        // GIVEN
        val timer = createTimerWithState()
        val task = createTask()

        // WHEN
        val updatedTimer = timer.linkTask(task)

        // THEN
        assertEquals(task, updatedTimer.linkedTask)
    }

    @Test
    fun `unlinkTask removes linked task`() {
        // GIVEN
        val task = createTask()
        val timer = createTimerWithState().linkTask(task)

        // WHEN
        val updatedTimer = timer.unlinkTask()

        // THEN
        assertEquals(null, updatedTimer.linkedTask)
    }

    @Test
    fun `unlinkTask fails if no task is linked`() {
        // GIVEN
        val timer = createTimerWithState()

        // WHEN / THEN
        assertFailsWith<IllegalArgumentException> {
            timer.unlinkTask()
        }
    }

    private fun createTimerWithState(
        state: FocusDividendTimerState = FocusDividendTimerState.Earning(initialTime, null),
    ): FocusDividendTimer {
        return FocusDividendTimer(
            id = TimerId.factory.createOrThrow(1),
            name = TimerName.factory.createOrThrow("Focus Dividend Timer"),
            creationTime = initialTime,
            state = state,
            linkedTask = null,
        )
    }

    private fun createTask(): LinkedTimerTask {
        return LinkedTimerTask(
            id = LinkedTaskId.factory.createOrThrow(1),
            name = LinkedTaskName.factory.createOrThrow("Test Task"),
            creationTime = initialTime,
            dueTime = initialTime + 30.minutes
        )
    }
}
