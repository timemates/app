package app.timemate.client.core.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule


@JvmInline
value class TimerName private constructor(
    val string: String,
) {
    companion object Companion {
        val factory: ValueFactory<TimerName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(1..50)),
            constructor = { TimerName(it) },
        )
    }
}