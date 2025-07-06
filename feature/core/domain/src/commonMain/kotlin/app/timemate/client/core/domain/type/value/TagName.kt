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
        val lengthRange: IntRange = 1..50

        val factory: ValueFactory<TagName, String> = factory(
            rules = listOf(StringLengthRangeValidationRule(lengthRange)),
            constructor = ::TagName,
        )
    }
}
