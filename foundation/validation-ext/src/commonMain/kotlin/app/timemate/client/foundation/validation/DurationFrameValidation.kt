package app.timemate.client.foundation.validation

import com.y9vad9.ktiny.kotlidator.CreationFailure
import com.y9vad9.ktiny.kotlidator.ValidationResult
import com.y9vad9.ktiny.kotlidator.rule.ValidationRule
import kotlin.time.Duration

public class DurationFrameValidation(
    private val range: ClosedRange<Duration>,
) : ValidationRule<Duration> {
    private val failure = DurationFrameFailure(range)
    override fun validate(value: Duration): ValidationResult {
        return when (value in range) {
            true -> ValidationResult.valid()
            else -> ValidationResult.invalid(failure)
        }
    }
}

public class DurationFrameFailure(
    @Suppress("unused")
    public val range: ClosedRange<Duration>,
) : CreationFailure {
    override val message: String = "Required duration range '$range' is violated."
}