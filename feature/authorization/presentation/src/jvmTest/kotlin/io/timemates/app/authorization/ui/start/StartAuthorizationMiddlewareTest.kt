package io.timemates.app.authorization.ui.start

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationMiddleware
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.foundation.random.nextString
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random
import kotlin.test.Test

class StartAuthorizationMiddlewareTest {
    private val stateStore: StateStore<State> = mockk()
    private val middleware: StartAuthorizationMiddleware = StartAuthorizationMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(Effect.TooManyAttempts, Effect.Failure(Exception()))
        every { stateStore.state } returns MutableStateFlow(State(isLoading = true))


        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, stateStore) }
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
        every { stateStore.state } returns MutableStateFlow(State(isLoading = true))

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, stateStore) }
            // THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect doesn't change loading status, but it should."
                }
            }
    }
}