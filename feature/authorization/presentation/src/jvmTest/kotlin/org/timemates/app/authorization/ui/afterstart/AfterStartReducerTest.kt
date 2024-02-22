package org.timemates.app.authorization.ui.afterstart

import io.mockk.mockk
import io.mockk.verify
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.test.TestScope
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartReducer
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.State
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.foundation.random.nextString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AfterStartReducerTest {

    private val verificationHash: VerificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
    private val sendEffect: (AfterStartScreenComponent.Effect) -> Unit = mockk(relaxed = true)
    private val reducer: AfterStartReducer = AfterStartReducer(verificationHash)
    private val coroutineScope = TestScope()

    @Test
    fun `reducing event NextClicked event should not update the state`() {
        // GIVEN
        val state = State
        val event = AfterStartScreenComponent.Event.NextClicked

        // WHEN
        val resultState = reducer.reduce(state, event, coroutineScope, sendEffect)

        // THEN
        verify { sendEffect(AfterStartScreenComponent.Effect.NavigateToConfirmation(verificationHash)) }
        assertEquals(state, resultState)
    }

    @Test
    fun `reducing event OnChangeEmailClicked event should not update the state`() {
        // GIVEN
        val state = State
        val event = AfterStartScreenComponent.Event.OnChangeEmailClicked

        // WHEN
        val resultState = reducer.reduce(state, event, coroutineScope, sendEffect)

        // THEN
        verify { sendEffect(AfterStartScreenComponent.Effect.OnChangeEmailClicked) }
        assertEquals(state, resultState)
    }
}
