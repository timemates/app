package org.timemates.app.feature.common.failures

import androidx.compose.runtime.Stable
import org.timemates.app.localization.Strings
import org.timemates.sdk.common.constructor.CreationFailure

@Stable
fun CreationFailure.getCommonDisplayMessage(strings: Strings): String {
    return when (this) {
        is CreationFailure.BlankValueFailure -> strings.fieldCannotBeEmpty
        is CreationFailure.MinValueFailure<*> -> strings.minValueFailure(size)
        is CreationFailure.PatternFailure -> strings.patternFailure
        is CreationFailure.LengthExactFailure -> strings.lengthExactFailure(size)
        is CreationFailure.LengthRangeFailure -> strings.lengthRangeFailure(range)
        is CreationFailure.ValueRangeFailure<*> ->
            strings.valueRangeFailure(range.start, range.endInclusive)

        is CreationFailure.CompoundFailure -> error("stub!")
    }
}

@Stable
fun List<CreationFailure>.getRepresentative(strings: Strings): String {
    return joinToString("\n") { it.getCommonDisplayMessage(strings) }
}