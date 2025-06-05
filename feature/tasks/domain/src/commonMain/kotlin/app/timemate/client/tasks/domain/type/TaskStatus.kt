package app.timemate.client.tasks.domain.type

import app.timemate.client.tasks.domain.type.value.TaskId
import app.timemate.client.tasks.domain.type.value.TaskStatusId
import app.timemate.client.tasks.domain.type.value.TaskStatusName

sealed interface TaskStatus {
    val id: TaskStatusId

    sealed interface Builtin : TaskStatus {
        data object Planed : Builtin {
            override val id: TaskStatusId = TaskStatusId.PLANED
        }

        data object InProgress : Builtin {
            override val id: TaskStatusId = TaskStatusId.IN_PROGRESS
        }

        data object Paused : Builtin {
            override val id: TaskStatusId = TaskStatusId.PAUSED
        }

        data object Done : Builtin {
            override val id: TaskStatusId = TaskStatusId.DONE
        }
    }

    data class Custom(override val id: TaskStatusId, val name: TaskStatusName) : TaskStatus
}