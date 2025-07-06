package app.timemate.client.timers.domain.type.settings.value

import app.timemate.client.foundation.validation.DurationFrameValidation
import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@JvmInline
value class PomodoroLongBreakTime private constructor(
    val duration: Duration
) {
    companion object {
        val MIN_TIME: Duration = 3.minutes
        val MAX_TIME: Duration = 1.hours

        val TIME_RANGE: ClosedRange<Duration> = MIN_TIME..MAX_TIME

        val factory: ValueFactory<PomodoroLongBreakTime, Duration> = factory(
            rules = listOf(DurationFrameValidation(TIME_RANGE)),
            constructor = ::PomodoroLongBreakTime,
        )
    }
}
