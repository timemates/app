package app.timemate.client.timers.domain.type.external

import app.timemate.client.tasks.domain.type.value.LinkedTaskId
import app.timemate.client.tasks.domain.type.value.LinkedTaskName
import kotlin.time.Instant

class LinkedTimerTask(
    val id: LinkedTaskId,
    val name: LinkedTaskName,
    val creationTime: Instant,
    val dueTime: Instant,
)