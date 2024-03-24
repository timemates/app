package org.timemates.app.feature.common

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import org.timemates.app.feature.common.failures.getRepresentative
import org.timemates.app.localization.Strings
import org.timemates.sdk.common.constructor.CreationFailure
import org.timemates.sdk.common.constructor.Factory
import org.timemates.sdk.common.constructor.results.SafeCreationResult
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Immutable
sealed interface Input<TRaw> {
    val value: TRaw

    data class Invalid<TRaw>(
        override val value: TRaw,
        val failures: List<CreationFailure>,
    ) : Input<TRaw>

    @JvmInline
    value class Valid<TRaw>(override val value: TRaw) : Input<TRaw>

    @JvmInline
    value class Unknown<TRaw>(override val value: TRaw) : Input<TRaw>

    @Stable
    fun <TBoxed> validated(factory: Factory<TBoxed, TRaw>): Input<TRaw> {
        return when (val result = factory.createSafe(value)) {
            is SafeCreationResult.Invalid -> Invalid(value, result.failures)
            is SafeCreationResult.Valid -> Valid(value)
        }
    }
}

@Stable
fun <TRaw> input(value: TRaw): Input<TRaw> = Input.Unknown(value)

@OptIn(ExperimentalContracts::class)
@Stable
fun <TRaw> Input<TRaw>.isValid(): Boolean {
    contract {
        returns(true) implies (this@isValid is Input.Valid<TRaw>)
        returns(false) implies (this@isValid !is Input.Valid<TRaw>)
    }

    return this is Input.Valid<TRaw>
}

@OptIn(ExperimentalContracts::class)
@Stable
fun <TRaw> Input<TRaw>.isInvalid(): Boolean {
    contract {
        returns(true) implies (this@isInvalid is Input.Invalid<TRaw>)
        returns(false) implies (this@isInvalid !is Input.Invalid<TRaw>)
    }

    return this is Input.Invalid<TRaw>
}

@Stable
fun <TRaw> Input<TRaw>.getFailuresIfPresent(strings: Strings): String? {
    return if (isInvalid())
        failures.getRepresentative(strings)
    else null
}