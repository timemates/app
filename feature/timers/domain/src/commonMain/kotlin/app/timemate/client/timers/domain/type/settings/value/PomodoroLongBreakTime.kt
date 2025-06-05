package app.timemate.client.timers.domain.type.settings.value

import app.timemate.client.foundation.validation.DurationFrameValidation
import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@JvmInline
value class PomodoroLongBreakTime private constructor(
    val duration: Duration
) {
    companion object Companion {
        val factory: ValueFactory<PomodoroLongBreakTime, Duration> = factory(
            rules = listOf(DurationFrameValidation(3.minutes..1.hours)),
            constructor = ::PomodoroLongBreakTime,
        )
    }
}