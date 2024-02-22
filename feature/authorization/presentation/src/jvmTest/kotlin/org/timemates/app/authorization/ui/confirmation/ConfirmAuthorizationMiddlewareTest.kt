package org.timemates.app.authorization.ui.confirmation

import io.mockk.mockk
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationMiddleware
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmAuthorizationMiddlewareTest {
    private val middleware = ConfirmAuthorizationMiddleware()

    @Test
    fun `onEffect with Effect Failure should update state with isLoading false`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effect = Effect.Failure(RuntimeException("Authorization failed"))

        // WHEN
        val newState = middleware.onEffect(effect, initialState)

        // THEN
        assertEquals(
            expected = initialState.copy(isLoading = false),
            actual = newState
        )
    }

    @Test
    fun `onEffect with Effect TooManyAttempts should update state with isLoading false`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effect = Effect.TooManyAttempts

        // WHEN
        val newState = middleware.onEffect(effect, initialState)

        // THEN
        assertEquals(
            expected = initialState.copy(isLoading = false),
            actual = newState
        )
    }

    @Test
    fun `navigation effects should change isLoading status`() {
        // GIVEN
        val initialState = State(isLoading = true)
        val effects = listOf(
            mockk<Effect.NavigateToCreateAccount>(),
            mockk<Effect.NavigateToHome>(),
        )

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, initialState) }
            .forEach { (effect, state) ->
                // THEN
                assertEquals(
                    expected = initialState.copy(isLoading = false),
                    actual = state,
                    message = "${effect::class.qualifiedName} should return same state"
                )
            }
    }
}
