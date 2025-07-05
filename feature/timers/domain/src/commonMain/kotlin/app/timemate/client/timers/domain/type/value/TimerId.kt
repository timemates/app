package app.timemate.client.timers.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule

@JvmInline
value class TimerId private constructor(
    val long: Long,
) {
    companion object {
        const val MIN_VALUE: Long = 0

        val factory: ValueFactory<TimerId, Long> = factory(
            rules = listOf(MinValueValidationRule(MIN_VALUE)),
            constructor = { TimerId(it) },
        )
    }
}
