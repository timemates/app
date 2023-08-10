package io.timemates.app.authorization.ui.initial_authorization

import io.mockk.mockk
import io.mockk.verify
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InitialAuthorizationReducerTest {
    private val sendEffect: (Effect) -> Unit = mockk(relaxed = true)
    private val reducer: InitialAuthorizationReducer = InitialAuthorizationReducer()

    @Test
    fun `reducing event OnStartClicked event should not update the state`() {
        //GIVEN
        val state: EmptyState = EmptyState
        val event: Event.OnStartClicked = Event.OnStartClicked

        //WHEN
        val resultState = reducer.reduce(state, event, sendEffect)

        //THEN
        verify { sendEffect(Effect.NavigateToStart) }
        assertEquals(state, resultState)
    }
}