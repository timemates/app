package org.timemates.app.timers.ui.timers_list

import org.junit.jupiter.api.Test
import org.timemates.app.timers.ui.timers_list.mvi.TimersListMiddleware
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.State

class TimersListMiddlewareTest {
    private val middleware: TimersListMiddleware = TimersListMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(
            TimersListScreenComponent.Effect.Failure(Exception()),
            TimersListScreenComponent.Effect.NoMoreTimers
        )

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, State(isLoading = true)) }
            // THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false."
                }
            }
    }
}
