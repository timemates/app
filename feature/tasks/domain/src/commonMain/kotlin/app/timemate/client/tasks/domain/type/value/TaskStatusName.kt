package app.timemate.client.tasks.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule


@JvmInline
value class TaskStatusName private constructor(
    val string: String,
) {
    companion object {
        val factory: ValueFactory<TaskStatusName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(1..100)),
            constructor = { TaskStatusName(it) },
        )
    }
}