package app.timemate.client.tasks.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule


@JvmInline
value class TaskStatusId private constructor(
    val long: Long,
) {
    companion object {
        val factory: ValueFactory<TaskStatusId, Long> = factory(
            rules = listOf(MinValueValidationRule(-3)),
            constructor = { TaskStatusId(it) },
        )

        val PLANED: TaskStatusId = TaskStatusId(-3)
        val IN_PROGRESS: TaskStatusId = TaskStatusId(-2)
        val PAUSED: TaskStatusId = TaskStatusId(-1)
        val DONE: TaskStatusId = TaskStatusId(0)
    }
}