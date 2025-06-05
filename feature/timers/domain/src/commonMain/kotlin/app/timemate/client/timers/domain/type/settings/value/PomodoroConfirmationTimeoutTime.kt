package app.timemate.client.timers.domain.type.settings.value

import app.timemate.client.foundation.validation.DurationFrameValidation
import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@JvmInline
value class PomodoroConfirmationTimeoutTime private constructor(
    val duration: Duration
) {
    companion object Companion {
        val factory: ValueFactory<PomodoroConfirmationTimeoutTime, Duration> = factory(
            rules = listOf(DurationFrameValidation(5.seconds..5.minutes)),
            constructor = ::PomodoroConfirmationTimeoutTime,
        )
    }
}