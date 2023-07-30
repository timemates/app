package io.timemates.app.authorization.ui.confirmation

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationsReducer
import io.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import io.timemates.app.authorization.validation.ConfirmationCodeValidator
import io.timemates.app.foundation.random.nextString
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConfirmAuthorizationReducerTest {
    private val authorizationsRepository: AuthorizationsRepository = mockk()
    private val confirmationCodeValidator: ConfirmationCodeValidator = mockk()
    private val confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase = mockk()
    private val coroutineScope = TestScope()
    private val verificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
    private val reducer = ConfirmAuthorizationsReducer(
        verificationHash,
        confirmEmailAuthorizationUseCase,
        confirmationCodeValidator,
        coroutineScope,
    )

    @Test
    fun `reducing Event OnConfirmClicked with valid code should update state and invoke confirm`() {
        // GIVEN
        val state = State(code = "12345678", isLoading = false)
        every { confirmationCodeValidator.validate(any()) } returns
            ConfirmationCodeValidator.Result.Success

        // WHEN
        val nextState = reducer.reduce(state, Event.OnConfirmClicked) {}

        // THEN
        assertTrue(nextState.isLoading)
        assertFalse(nextState.isCodeInvalid)
        assertFalse(nextState.isCodeSizeInvalid)
        assertEquals("12345678", nextState.code)
    }

    @Test
    fun `reducing Event OnConfirmClicked with invalid code size should update state`() {
        // GIVEN
        val state = State(code = "12345", isLoading = false)
        every { confirmationCodeValidator.validate(any()) } returns
            ConfirmationCodeValidator.Result.SizeIsInvalid

        // WHEN
        val nextState = reducer.reduce(state, Event.OnConfirmClicked) {}

        // THEN
        assertFalse(nextState.isLoading)
        assertFalse(nextState.isCodeInvalid)
        assertTrue(nextState.isCodeSizeInvalid)
        assertEquals("12345", nextState.code)
    }

    @Test
    fun `reducing Event CodeChange should update the code in state`() {
        // GIVEN
        val state = State(code = "123456", isLoading = false)
        val event = Event.CodeChange("654321")

        // WHEN
        val nextState = reducer.reduce(state, event) {}

        // THEN
        assertEquals("654321", nextState.code)
        assertFalse(nextState.isCodeInvalid)
        assertFalse(nextState.isCodeSizeInvalid)
        assertFalse(nextState.isLoading)
    }

    @Test
    fun `reducing Event OnConfirmClicked with invalid code should update state`() {
        // GIVEN
        val state = State(code = "invalid", isLoading = false)
        every { confirmationCodeValidator.validate(any()) } returns
            ConfirmationCodeValidator.Result.PatternFailure

        // WHEN
        val nextState = reducer.reduce(state, Event.OnConfirmClicked) {}

        // THEN
        assertFalse(nextState.isLoading)
        assertTrue(nextState.isCodeInvalid)
        assertFalse(nextState.isCodeSizeInvalid)
        assertEquals("invalid", nextState.code)
    }
}
