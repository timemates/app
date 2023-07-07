package io.timemates.app.authorization.ui.start

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationReducer
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import io.timemates.app.authorization.validation.EmailAddressValidator
import kotlinx.coroutines.test.TestScope
import kotlin.test.Test
import kotlin.test.assertEquals

class StartAuthorizationReducerTest {
    private val authorizeByEmailUseCase: AuthorizeByEmailUseCase = mockk()
    private val emailAddressValidator: EmailAddressValidator = mockk()
    private val email = "..." // doesn't matter, as we mock validator

    private val reducer = StartAuthorizationReducer(
        emailAddressValidator,
        authorizeByEmailUseCase,
        TestScope(),
    )

    @Test
    fun `OnStartClick with invalid email should set appropriate state`() {
        // GIVEN
        every { emailAddressValidator.validate(any()) } returns
            EmailAddressValidator.Result.PatternDoesNotMatch

        // WHEN
        val result = reducer.reduce(State(email = email), Event.OnStartClick) {}

        // THEN
        assertEquals(
            expected = State(
                email = email,
                isEmailInvalid = true,
                isEmailLengthSizeInvalid = false,
                isLoading = false,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnStartClick with invalid email size should set appropriate state`() {
        // GIVEN
        every { emailAddressValidator.validate(email) } returns
            EmailAddressValidator.Result.SizeViolation

        // WHEN
        val result = reducer.reduce(State(email = email), Event.OnStartClick) {}

        // THEN
        assertEquals(
            expected = State(
                email = email,
                isEmailInvalid = false,
                isEmailLengthSizeInvalid = true,
                isLoading = false,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnStartClick with valid email should set appropriate state`() {
        // GIVEN
        every { emailAddressValidator.validate(any()) } returns
            EmailAddressValidator.Result.Success

        // WHEN
        val result = reducer.reduce(State(email = email, isEmailInvalid = true), Event.OnStartClick) {}

        // THEN
        assertEquals(
            // if previously there was invalid email state, we should fix that
            // should also set loading state
            expected = State(
                email = email,
                isEmailInvalid = false,
                isEmailLengthSizeInvalid = false,
                isLoading = true
            ),
            actual = result,
        )
    }
}