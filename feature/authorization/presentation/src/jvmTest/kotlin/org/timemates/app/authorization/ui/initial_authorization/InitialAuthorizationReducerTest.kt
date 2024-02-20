package org.timemates.app.authorization.ui.initial_authorization

import io.mockk.mockk
import io.mockk.verify
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.reduce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InitialAuthorizationReducerTest {
    private val sendEffect: (Effect) -> Unit = mockk(relaxed = true)
    private val reducer: InitialAuthorizationReducer = InitialAuthorizationReducer()
    private val coroutineScope = TestScope()

    @Test
    fun `reducing event OnStartClicked event should not update the state`() {
        //GIVEN
        val state: EmptyState = EmptyState
        val event: Event.OnStartClicked = Event.OnStartClicked

        //WHEN
        val resultState = reducer.reduce(state, event, coroutineScope, sendEffect)

        //THEN
        verify { sendEffect(Effect.NavigateToStart) }
        assertEquals(state, resultState)
    }
}