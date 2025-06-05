package app.timemate.client.tasks.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule


@JvmInline
value class TaskId private constructor(
    val long: Long,
) {
    companion object {
        val factory: ValueFactory<TaskId, Long> = factory(
            rules = listOf(MinValueValidationRule(0)),
            constructor = { TaskId(it) },
        )
    }
}