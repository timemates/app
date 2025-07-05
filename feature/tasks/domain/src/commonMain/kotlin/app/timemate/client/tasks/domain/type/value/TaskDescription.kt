package app.timemate.client.tasks.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.jvm.JvmInline


@JvmInline
value class TaskDescription private constructor(
    val string: String,
) {
    companion object {
        val factory: ValueFactory<TaskDescription, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(1..10000)),
            constructor = { TaskDescription(it) },
        )
    }
}
