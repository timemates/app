package app.timemate.client.timers.domain.type.task

import app.timemate.client.timers.domain.type.task.value.LinkedTaskId
import app.timemate.client.timers.domain.type.task.value.LinkedTaskName
import kotlin.time.Instant

class LinkedTimerTask(
    val id: LinkedTaskId,
    val name: LinkedTaskName,
    val creationTime: Instant,
    val dueTime: Instant,
)
