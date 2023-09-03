package io.timemates.app.timers.ui.timers_list

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.timers_list.mvi.TimersListMiddleware
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Test

class TimersListMiddlewareTest {
    private val stateStore: StateStore<TimersListStateMachine.State> = mockk()
    private val middleware: TimersListMiddleware = TimersListMiddleware()

    @Test
    fun `effects produced by network operations should remove loading status`() {
        // GIVEN
        val effects = listOf(
            TimersListStateMachine.Effect.Failure(Exception()),
            TimersListStateMachine.Effect.NoMoreTimers
        )
        every { stateStore.state } returns MutableStateFlow(TimersListStateMachine.State(isLoading = true))

        // WHEN
        effects.map { effect -> effect to middleware.onEffect(effect, stateStore) }
            // THEN
            .forEach { (effect, state) ->
                assert(!state.isLoading) {
                    "${effect::class.simpleName} effect does not change loading status to false."
                }
            }
    }
}
