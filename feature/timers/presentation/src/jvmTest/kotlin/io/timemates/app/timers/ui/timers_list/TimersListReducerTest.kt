package io.timemates.app.timers.ui.timers_list

import io.mockk.mockk
import io.timemates.app.timers.ui.timers_list.mvi.TimersListReducer
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine
import io.timemates.app.users.usecases.GetUserTimersUseCase
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TimersListReducerTest {
    private val getUserTimersUseCase: GetUserTimersUseCase = mockk()

    private val reducer = TimersListReducer(
        getUserTimersUseCase = getUserTimersUseCase,
        coroutineScope = TestScope(),
    )

    @Test
    fun `Event Load must return the parameter isLoading true`() {
        // WHEN
        val result = reducer.reduce(TimersListStateMachine.State(), TimersListStateMachine.Event.Load) {}

        // THEN
        assertEquals(
            expected = TimersListStateMachine.State(
                isLoading = true,
            ),
            actual = result,
        )
    }
}
