package app.timemate.client.timers.domain.type

import app.timemate.client.timers.domain.type.value.TimerName
import app.timemate.client.timers.domain.type.task.LinkedTimerTask
import app.timemate.client.timers.domain.type.state.PomodoroTimerState
import app.timemate.client.timers.domain.type.value.TimerId
import kotlin.time.Instant

data class PomodoroTimer(
    override val id: TimerId,
    override val name: TimerName,
    override val creationTime: Instant,
    override val state: PomodoroTimerState,
    override val linkedTask: LinkedTimerTask,
) : Timer
