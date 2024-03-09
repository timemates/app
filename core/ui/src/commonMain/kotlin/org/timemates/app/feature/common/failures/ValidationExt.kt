package org.timemates.app.feature.common.failures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import org.timemates.app.localization.Strings
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.sdk.common.constructor.CreationFailure

@Stable
fun CreationFailure.getCommonDisplayMessage(strings: Strings): String {
    return when (this) {
        is CreationFailure.BlankValueFailure -> strings.fieldCannotBeEmpty
        is CreationFailure.MinValueFailure -> strings.minValueFailure(size)
        is CreationFailure.PatternFailure -> strings.patternFailure
        is CreationFailure.SizeExactFailure -> strings.sizeExactFailure(size)
        is CreationFailure.SizeRangeFailure -> strings.sizeRangeFailure(range)
    }
}

@Composable
@Stable
fun CreationFailure.getCommonDisplayMessage(): String {
    return getCommonDisplayMessage(LocalStrings.current)
}