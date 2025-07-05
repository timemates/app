package app.timemate.client.tasks.domain.type

import app.timemate.client.tasks.domain.type.value.TaskDescription
import app.timemate.client.tasks.domain.type.value.TaskId
import app.timemate.client.tasks.domain.type.value.TaskName
import kotlin.time.Instant

data class Task(
    val id: TaskId,
    val name: TaskName,
    val description: TaskDescription,
    val status: TaskStatus,
    val creationTime: Instant,
    val dueTime: Instant,
)
