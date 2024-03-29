package org.timemates.app.authorization.ui.confirmation

import io.mockk.every
import io.mockk.mockk
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Intent
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import org.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import org.timemates.app.authorization.validation.ConfirmationCodeValidator
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.foundation.random.nextString
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConfirmAuthorizationReducerTest {
    private val confirmationCodeValidator: ConfirmationCodeValidator = mockk()
    private val confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase = mockk()
    private val coroutineScope = TestScope()
    private val verificationHash = VerificationHash.factory.createOrThrow(Random.nextString(VerificationHash.SIZE))
    private val reducer = ConfirmAuthorizationsReducer(
        verificationHash,
        confirmEmailAuthorizationUseCase,
        confirmationCodeValidator,
    )

    @Test
    fun `reducing Event OnConfirmClicked with valid code should update state and invoke confirm`() {
        // GIVEN
        val state = State(code = "12345678", isLoading = false)
        every { confirmationCodeValidator.validate(any()) } returns
            ConfirmationCodeValidator.Result.Success

        // WHEN
        val nextState = reducer.reduce(state, Intent.OnConfirmClicked, coroutineScope) {}

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
        val nextState = reducer.reduce(state, Intent.OnConfirmClicked, coroutineScope) {}

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
        val intent = Intent.CodeChange("654321")

        // WHEN
        val nextState = reducer.reduce(state, intent, coroutineScope) {}

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
        val nextState = reducer.reduce(state, Intent.OnConfirmClicked, coroutineScope) {}

        // THEN
        assertFalse(nextState.isLoading)
        assertTrue(nextState.isCodeInvalid)
        assertFalse(nextState.isCodeSizeInvalid)
        assertEquals("invalid", nextState.code)
    }
}
