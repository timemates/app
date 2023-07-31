package io.timemates.app.authorization.ui.new_account_info.mvi

import io.mockk.mockk
import io.mockk.verify
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartReducer
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.random.nextString
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class NewAccountInfoReducerTest {
    private val verificationHash: VerificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
    private val sendEffect: (AfterStartStateMachine.Effect) -> Unit = mockk(relaxed = true)
    private val reducer: AfterStartReducer = AfterStartReducer(verificationHash)

    @Test
    fun `reducing event NextClicked event should not update the state`() {
        // GIVEN
        val state: EmptyState = EmptyState
        val event: AfterStartStateMachine.Event.NextClicked = AfterStartStateMachine.Event.NextClicked

        // WHEN
        val resultState = reducer.reduce(state, event, sendEffect)

        // THEN
        verify { sendEffect(AfterStartStateMachine.Effect.NavigateToConfirmation(verificationHash)) }
        assertEquals(state, resultState)
    }

    @Test
    fun `reducing event OnBackClicked event should not update the state`() {
        // GIVEN
        val state: EmptyState = EmptyState

        val event: AfterStartStateMachine.Event.OnChangeEmailClicked = AfterStartStateMachine.Event.OnChangeEmailClicked

        // WHEN
        val resultState = reducer.reduce(state, event, sendEffect)

        // THEN
        verify { sendEffect(AfterStartStateMachine.Effect.OnChangeEmailClicked) }
        assertEquals(state, resultState)
    }
}