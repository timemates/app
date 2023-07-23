package io.timemates.app.authorization.ui.confirmation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationMiddleware
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.StateStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmAuthorizationMiddlewareTest {

    private val store: StateStore<State> = mockk(relaxed = true)
    private val middleware = ConfirmAuthorizationMiddleware()

    @Test
    fun `onEffect with Effect Failure should update state with isLoading false`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effect = Effect.Failure(RuntimeException("Authorization failed"))

        every { store.state } returns MutableStateFlow(initialState)

        // WHEN
        val newState = middleware.onEffect(effect, store)

        // THEN
        assertEquals(
            expected = initialState.copy(isLoading = false),
            actual = newState
        )
        verify { store.state }
    }

    @Test
    fun `onEffect with Effect TooManyAttempts should update state with isLoading false`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effect = Effect.TooManyAttempts

        every { store.state } returns MutableStateFlow(initialState)

        // WHEN
        val newState = middleware.onEffect(effect, store)

        // THEN
        assertEquals(
            expected = initialState.copy(isLoading = false),
            actual = newState
        )
        verify { store.state }
    }

    @Test
    fun `onEffect with other effects should return the current state`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effects = listOf(
            mockk<Effect.NavigateToCreateAccount>(),
            mockk<Effect.NavigateToHome>(),
        )

        every { store.state } returns MutableStateFlow(initialState)

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, store) }
            .forEach { (effect, state) ->
                // THEN
                assertEquals(
                    expected = initialState,
                    actual = state,
                    message = "${effect::class.qualifiedName} should return same state"
                )
            }
    }
}
