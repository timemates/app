package org.timemates.app.timers.ui.timers_list

import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent
import org.timemates.app.users.usecases.GetUserTimersUseCase
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
            state = TimersListScreenComponent.State(),
            event = TimersListScreenComponent.Intent.Load,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimersListScreenComponent.State(
                isLoading = true,
            ),
            actual = result,
        )
    }
}
