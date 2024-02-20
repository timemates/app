package org.timemates.app.common.mvi.middleware

import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import org.timemates.app.feature.common.middleware.AuthorizationFailureMiddleware.AuthorizationFailureEffect
import org.timemates.app.feature.common.handler.OnAuthorizationFailedHandler
import org.timemates.app.feature.common.middleware.AuthorizationFailureMiddleware
import org.timemates.app.foundation.mvi.StateStore
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthorizationFailureMiddlewareTest {

    private val onAuthorizationFailed: OnAuthorizationFailedHandler = mockk(relaxed = true)
    private val middleware = AuthorizationFailureMiddleware<TestState, TestEffect>(onAuthorizationFailed)
    private val stateFlow: MutableStateFlow<TestState> = MutableStateFlow(TestState.Initial)

    private sealed class TestEffect : UiEffect {
        data class AuthorizationFailure(
            override val exception: UnauthorizedException,
        ) : TestEffect(), AuthorizationFailureEffect

        data object AnyOther : TestEffect()
    }

    private sealed class TestState : UiState {
        data object Initial : TestState()
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `child effect of AuthorizationFailureEffect should call onAuthorizationFailed`() {
        // GIVEN
        val exception = UnauthorizedException("Authorization failed")
        val effect = TestEffect.AuthorizationFailure(exception)

        // WHEN
        middleware.onEffect(effect, createStateStore())

        // THEN
        verify(exactly = 1) { onAuthorizationFailed.onFailed(exception) }
    }

    @Test
    fun `onEffect with other effect should return current state`() {
        // GIVEN
        val effect = TestEffect.AnyOther

        // WHEN
        val result = middleware.onEffect(effect, createStateStore())

        // THEN
        assertEquals(expected = TestState.Initial, actual = result)
    }

    private fun createStateStore(): StateStore<TestState> {
        return object : StateStore<TestState> {
            override val state: StateFlow<TestState>
                get() = stateFlow
        }
    }
}
