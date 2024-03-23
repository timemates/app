package org.timemates.app.timers.ui.settings

import org.junit.jupiter.api.Test
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent

class TimerSettingsMiddlewareTest {
    private val middleware: TimerSettingsMiddleware = TimerSettingsMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        //GIVEN
        val effects = listOf(TimerSettingsScreenComponent.Effect.Failure(Exception()))

        //WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, TimerSettingsScreenComponent.State(isLoading = true)) }
            //THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false"
                }
            }
    }
}
