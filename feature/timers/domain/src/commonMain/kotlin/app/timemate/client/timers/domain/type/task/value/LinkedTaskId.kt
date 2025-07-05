package app.timemate.client.timers.domain.type.task.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule


@JvmInline
value class LinkedTaskId private constructor(
    val long: Long,
) {
    companion object {
        val factory: ValueFactory<LinkedTaskId, Long> = factory(
            rules = listOf(MinValueValidationRule(0)),
            constructor = { LinkedTaskId(it) },
        )
    }
}
