package app.timemate.client.timers.domain.type.settings.value

import app.timemate.client.foundation.validation.DurationFrameValidation
import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@JvmInline
value class PomodoroShortBreakTime private constructor(
    val duration: Duration
) {
    companion object Companion {
        val factory: ValueFactory<PomodoroShortBreakTime, Duration> = factory(
            rules = listOf(DurationFrameValidation(1.minutes..20.minutes)),
            constructor = ::PomodoroShortBreakTime,
        )
    }
}
