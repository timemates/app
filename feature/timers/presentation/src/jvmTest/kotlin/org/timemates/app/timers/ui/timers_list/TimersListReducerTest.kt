package org.timemates.app.timers.ui.timers_list

import io.mockk.mockk
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.timers.ui.timers_list.mvi.TimersListReducer
import org.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine
import org.timemates.app.users.usecases.GetUserTimersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TimersListReducerTest {
    private val getUserTimersUseCase: GetUserTimersUseCase = mockk()

    private val reducer = TimersListReducer(
        getUserTimersUseCase = getUserTimersUseCase,
    )
    private val scope = TestScope()

    @Test
    fun `Event Load must return the parameter isLoading true`() {
        // WHEN
        val result = reducer.reduce(
            state = TimersListStateMachine.State(),
            event = TimersListStateMachine.Event.Load,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimersListStateMachine.State(
                isLoading = true,
            ),
            actual = result,
        )
    }
}
