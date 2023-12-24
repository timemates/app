package io.timemates.app.timers.ui.settings

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsMiddleware
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Test

class TimerSettingsMiddlewareTest {
    private val stateStore: StateStore<TimerSettingsStateMachine.State> = mockk()
    private val middleware: TimerSettingsMiddleware = TimerSettingsMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        //GIVEN
        val effects = listOf(TimerSettingsStateMachine.Effect.Failure(Exception()))
        every { stateStore.state } returns MutableStateFlow(TimerSettingsStateMachine.State(isLoading = true))

        //WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, stateStore) }
            //THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false"
                }
            }
    }
}
