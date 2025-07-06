package app.timemate.client.core.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.jvm.JvmInline

@JvmInline
value class TagName private constructor(
    val string: String,
) {
    companion object {
        const val MIN_LENGTH: Int = 1
        const val MAX_LENGTH: Int = 50

        val RANGE: IntRange = MIN_LENGTH..MAX_LENGTH

        val factory: ValueFactory<TagName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(RANGE)),
            constructor = ::TagName,
        )
    }
}
