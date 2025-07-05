package app.timemate.client.timers.domain.type.task.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule


@JvmInline
value class LinkedTaskName private constructor(
    val string: String,
) {
    companion object Companion {
        val factory: ValueFactory<LinkedTaskName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(1..100)),
            constructor = { LinkedTaskName(it) },
        )
    }
}
