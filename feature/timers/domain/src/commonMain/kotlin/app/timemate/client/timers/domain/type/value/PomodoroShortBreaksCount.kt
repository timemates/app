package app.timemate.client.timers.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import kotlin.jvm.JvmInline

@JvmInline
value class PomodoroShortBreaksCount private constructor(
    val int: Int,
) {
    companion object Companion {
        const val MIN_VALUE: Int = 0

        val factory: ValueFactory<PomodoroShortBreaksCount, Int> = factory(
            rules = listOf(MinValueValidationRule(MIN_VALUE)),
            constructor = { PomodoroShortBreaksCount(it) },
        )

        val DEFAULT_LONG_BREAK_AFTER = PomodoroShortBreaksCount(4)
    }
}
