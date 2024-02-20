package org.timemates.app.authorization.ui.configure_account

import io.mockk.every
import io.mockk.mockk
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountReducer
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Event
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import org.timemates.app.authorization.usecases.CreateNewAccountUseCase
import org.timemates.app.authorization.validation.UserDescriptionValidator
import org.timemates.app.authorization.validation.UserNameValidator
import org.timemates.app.foundation.mvi.reduce
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.CoroutineScope
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
    private val coroutineScope = TestScope()
    private val sendEffect: (ConfigureAccountStateMachine.Effect) -> Unit = mockk(relaxed = true)

    private val reducer = ConfigureAccountReducer(
        verificationHash,
        createNewAccountUseCase,
        userNameValidator,
        userDescriptionValidator,
    )

    @Test
    fun `OnDoneClicked with valid name should set appropriate state`() {
        // GIVEN
        every { userNameValidator.validate(any()) } returns
            UserNameValidator.Result.Success
        every { userDescriptionValidator.validate(any()) } returns
            UserDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(
            State(name = validName, aboutYou = validDescription),
            Event.OnDoneClicked,
            coroutineScope,
            sendEffect,
        )

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
        val result = reducer.reduce(
            State(name = validName, aboutYou = validDescription),
            Event.OnDoneClicked,
            coroutineScope,
            sendEffect,
        )

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
        val result = reducer.reduce(
            State(name = invalidName, aboutYou = validDescription),
            Event.OnDoneClicked,
            coroutineScope,
            sendEffect,
        )

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
        val result = reducer.reduce(
            state = State(name = validName, aboutYou = invalidDescription),
            event = Event.OnDoneClicked,
            machineScope = coroutineScope,
            sendEffect = sendEffect,
        )

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