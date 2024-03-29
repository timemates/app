package org.timemates.app.authorization.ui.afterstart

import io.mockk.mockk
import io.mockk.verify
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.test.TestScope
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.State
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.foundation.random.nextString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AfterStartReducerTest {

    private val verificationHash: VerificationHash = VerificationHash.factory.createOrThrow(Random.nextString(VerificationHash.SIZE))
    private val sendEffect: (AfterStartScreenComponent.Action) -> Unit = mockk(relaxed = true)
    private val reducer: AfterStartReducer = AfterStartReducer(verificationHash)
    private val coroutineScope = TestScope()

    @Test
    fun `reducing event NextClicked event should not update the state`() {
        // GIVEN
        val state = State
        val intent = AfterStartScreenComponent.Intent.NextClicked

        // WHEN
        val resultState = reducer.reduce(state, intent, coroutineScope, sendEffect)

        // THEN
        verify { sendEffect(AfterStartScreenComponent.Action.NavigateToConfirmation(verificationHash)) }
        assertEquals(state, resultState)
    }

    @Test
    fun `reducing event OnChangeEmailClicked event should not update the state`() {
        // GIVEN
        val state = State
        val intent = AfterStartScreenComponent.Intent.OnChangeEmailClicked

        // WHEN
        val resultState = reducer.reduce(state, intent, coroutineScope, sendEffect)

        // THEN
        verify { sendEffect(AfterStartScreenComponent.Action.OnChangeEmailClicked) }
        assertEquals(state, resultState)
    }
}
