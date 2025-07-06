package app.timemate.client.timers.domain.type.settings.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import kotlin.jvm.JvmInline

@JvmInline
value class PomodoroLongBreakPerShortBreaksCount private constructor(
    val int: Int,
) {
    companion object {
        const val MIN_VALUE = 2

        val DEFAULT: PomodoroLongBreakPerShortBreaksCount = PomodoroLongBreakPerShortBreaksCount(4)

        val factory: ValueFactory<PomodoroLongBreakPerShortBreaksCount, Int> = factory(
            rules = listOf(MinValueValidationRule(MIN_VALUE)),
            constructor = ::PomodoroLongBreakPerShortBreaksCount,
        )
    }
}