package app.timemate.client.timers.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.jvm.JvmInline

@JvmInline
value class TimerName private constructor(
    val string: String,
) {
    companion object {
        val factory: ValueFactory<TimerName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(1..50)),
            constructor = { TimerName(it) },
        )
    }
}
