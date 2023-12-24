package io.timemates.app.authorization.ui.configure_account

import io.mockk.every
import io.mockk.mockk
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountReducer
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Event
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.authorization.usecases.CreateNewAccountUseCase
import io.timemates.app.authorization.validation.UserDescriptionValidator
import io.timemates.app.authorization.validation.UserNameValidator
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConfigureAccountReducerTest {
    private val verificationHash: VerificationHash = mockk(relaxed = true)
    private val createNewAccountUseCase: CreateNewAccountUseCase = mockk()
    private val userNameValidator: UserNameValidator = mockk()
    private val userDescriptionValidator: UserDescriptionValidator = mockk()

    private val validName = "kotleni"
    private val validDescription = "Kotlin developer"
    private val invalidName = "ko"
    private val invalidDescription = ""

    private val reducer = ConfigureAccountReducer(
        verificationHash,
        createNewAccountUseCase,
        userNameValidator,
        userDescriptionValidator,
        TestScope(),
    )

    @Test
    fun `OnDoneClicked with valid name should set appropriate state`() {
        // GIVEN
        every { userNameValidator.validate(any()) } returns
            UserNameValidator.Result.Success
        every { userDescriptionValidator.validate(any()) } returns
            UserDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(State(name = validName, aboutYou = validDescription), Event.OnDoneClicked) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                aboutYou = validDescription,
                isNameSizeInvalid = false,
                isAboutYouSizeInvalid = false,
                isLoading = true
            ),
            actual = result
        )
    }

    @Test
    fun `OnDoneClicked with valid description should set appropriate state`() {
        // GIVEN
        every { userNameValidator.validate(any()) } returns
            UserNameValidator.Result.Success
        every { userDescriptionValidator.validate(any()) } returns
            UserDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(State(name = validName, aboutYou = validDescription), Event.OnDoneClicked) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                aboutYou = validDescription,
                isNameSizeInvalid = false,
                isAboutYouSizeInvalid = false,
                isLoading = true
            ),
            actual = result
        )
    }

    @Test
    fun `OnDoneClicked with invalid name should set appropriate state`() {
        // GIVEN
        every { userNameValidator.validate(any()) } returns
            UserNameValidator.Result.SizeViolation
        every { userDescriptionValidator.validate(any()) } returns
            UserDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(State(name = invalidName, aboutYou = validDescription), Event.OnDoneClicked) {}

        // THEN
        assertEquals(
            expected = State(
                name = invalidName,
                aboutYou = validDescription,
                isNameSizeInvalid = true,
                isAboutYouSizeInvalid = false,
                isLoading = false
            ),
            actual = result
        )
    }

    @Test
    fun `OnDoneClicked with invalid description should set appropriate state`() {
        // GIVEN
        every { userNameValidator.validate(any()) } returns
            UserNameValidator.Result.Success
        every { userDescriptionValidator.validate(any()) } returns
            UserDescriptionValidator.Result.SizeViolation

        // WHEN
        val result = reducer.reduce(State(name = validName, aboutYou = invalidDescription), Event.OnDoneClicked) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                aboutYou = invalidDescription,
                isNameSizeInvalid = false,
                isAboutYouSizeInvalid = true,
                isLoading = false
            ),
            actual = result
        )
    }
}