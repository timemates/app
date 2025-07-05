package app.timemate.client.timers.domain.type.tag.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.jvm.JvmInline

@JvmInline
value class TimerTagName private constructor(
    val string: String,
) {
    companion object {
        val LENGTH_RANGE: IntRange = 1..50

        val factory: ValueFactory<TimerTagName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(LENGTH_RANGE)),
            constructor = { TimerTagName(it) },
        )
    }
}
