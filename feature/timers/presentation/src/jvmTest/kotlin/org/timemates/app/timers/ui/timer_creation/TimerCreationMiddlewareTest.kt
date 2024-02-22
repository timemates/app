package org.timemates.app.timers.ui.timer_creation

import org.junit.jupiter.api.Test
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationMiddleware
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent.Effect
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent.State

class TimerCreationMiddlewareTest {
    private val middleware: TimerCreationMiddleware = TimerCreationMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        //GIVEN
        val effects = listOf(Effect.Failure(Exception()))

        //WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, State(isLoading = true)) }
            //THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false"
                }
            }
    }
}