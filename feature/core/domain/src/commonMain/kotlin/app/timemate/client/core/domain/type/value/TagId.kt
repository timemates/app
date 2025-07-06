package app.timemate.client.core.domain.type.value

import com.y9vad9.ktiny.kotlidator.ValueFactory
import kotlin.jvm.JvmInline
import com.y9vad9.ktiny.kotlidator.factory
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule


@JvmInline
value class TagId private constructor(
    val long: Long,
) {
    companion object {
        const val MINIMAL_VALUE = 0L

        val factory: ValueFactory<TagId, Long> = factory(
            rules = listOf(MinValueValidationRule(MINIMAL_VALUE)),
            constructor = ::TagId,
        )
    }
}
