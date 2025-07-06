package app.timemate.client.timers.domain.type

import app.timemate.client.timers.domain.type.value.TimerName
import app.timemate.client.timers.domain.type.task.LinkedTimerTask
import app.timemate.client.timers.domain.type.state.TimerState
import app.timemate.client.timers.domain.type.value.TimerId
import kotlin.time.Instant

sealed interface Timer {
    val id: TimerId
    val name: TimerName
    val creationTime: Instant
    val state: TimerState
    val linkedTask: LinkedTimerTask?
}
