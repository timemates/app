package app.timemate.client.timers.domain.type.tag.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule


@JvmInline
value class TimerTagId private constructor(
    val long: Long,
) {
    companion object {
        const val MIN_VALUE: Long = 0

        val factory: ValueFactory<TimerTagId, Long> = factory(
            rules = listOf(MinValueValidationRule(MIN_VALUE)),
            constructor = { TimerTagId(it) },
        )
    }
}