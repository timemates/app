package org.timemates.app.authorization.ui.configure_account

import io.mockk.mockk
import io.timemates.sdk.authorization.sessions.types.Authorization
import org.junit.jupiter.api.Test
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountMiddleware
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.State

class ConfigureAccountMiddlewareTest {
    private val middleware: ConfigureAccountMiddleware = ConfigureAccountMiddleware()
    private val authorization: Authorization = mockk()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(ConfigureAccountScreenComponent.Effect.Failure(Exception()))

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
    fun `effects not produced by network operations should not remove loading status`() {
        // GIVEN
        val effects = listOf(
            ConfigureAccountScreenComponent.Effect.NavigateToStart,
            ConfigureAccountScreenComponent.Effect.NavigateToHomePage(authorization)
        )

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, State(isLoading = true)) }
            // THEN
            .forEach { (effect, state) ->
                assert(state.isLoading) {
                    "${effect::class.simpleName} effect changes loading status regardless it shouldn't"
                }
            }
    }
}
