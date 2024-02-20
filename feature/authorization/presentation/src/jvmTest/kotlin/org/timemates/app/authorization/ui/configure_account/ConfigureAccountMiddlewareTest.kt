package org.timemates.app.authorization.ui.configure_account

import io.mockk.every
import io.mockk.mockk
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountMiddleware
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine
import org.timemates.app.foundation.mvi.StateStore
import io.timemates.sdk.authorization.sessions.types.Authorization
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Test

class ConfigureAccountMiddlewareTest {
    private val stateStore: StateStore<ConfigureAccountStateMachine.State> = mockk()
    private val middleware: ConfigureAccountMiddleware = ConfigureAccountMiddleware()
    private val authorization: Authorization = mockk()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(ConfigureAccountStateMachine.Effect.Failure(Exception()))
        every { stateStore.state } returns MutableStateFlow(ConfigureAccountStateMachine.State(isLoading = true))

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
    fun `effects not produced by network operations should not remove loading status`() {
        // GIVEN
        val effects = listOf(
            ConfigureAccountStateMachine.Effect.NavigateToStart,
            ConfigureAccountStateMachine.Effect.NavigateToHomePage(authorization)
        )
        every { stateStore.state } returns MutableStateFlow(ConfigureAccountStateMachine.State(isLoading = true))

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, stateStore) }
            // THEN
            .forEach { (effect, state) ->
                assert(state.isLoading) {
                    "${effect::class.simpleName} effect changes loading status regardless it shouldn't"
                }
            }
    }
}
