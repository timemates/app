package org.timemates.app.authorization.ui.start

import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.State
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationMiddleware
import org.timemates.app.foundation.random.nextString
import kotlin.random.Random
import kotlin.test.Test

class StartAuthorizationMiddlewareTest {
    private val middleware: StartAuthorizationMiddleware = StartAuthorizationMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(Effect.TooManyAttempts, Effect.Failure(Exception()))


        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, State(isLoading = true)) }
            // THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false."
                }
            }
    }

    @Test
    fun `effects for navigation should remove loading status`() {
        // GIVEN
        val effects = listOf(
            Effect.NavigateToConfirmation(
                VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
            )
        )

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, State(isLoading = true)) }
            // THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect doesn't change loading status, but it should."
                }
            }
    }
}