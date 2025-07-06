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
            rules = listOf(MinValueValidationRule(-4)),
            constructor = { TaskStatusId(it) },
        )

        val PLANED: TaskStatusId = TaskStatusId(-4)
        val IN_PROGRESS: TaskStatusId = TaskStatusId(-3)
        val PAUSED: TaskStatusId = TaskStatusId(-2)
        val DONE: TaskStatusId = TaskStatusId(-1)

        val BUILTIN_IDS: List<TaskStatusId> = listOf(
            PLANED, IN_PROGRESS, PAUSED, DONE,
        )
    }

    fun isNotBuiltin(): Boolean = this !in BUILTIN_IDS
    fun isBuiltin(): Boolean = !isNotBuiltin()
}
