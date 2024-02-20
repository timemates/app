package org.timemates.app.timers.ui.timer_creation

import io.mockk.every
import io.mockk.mockk
import org.timemates.app.foundation.mvi.StateStore
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationMiddleware
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.Effect
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.State
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Test

class TimerCreationMiddlewareTest {
    private val stateStore: StateStore<State> = mockk()
    private val middleware: TimerCreationMiddleware = TimerCreationMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        //GIVEN
        val effects = listOf(Effect.Failure(Exception()))
        every { stateStore.state } returns MutableStateFlow(State(isLoading = true))

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