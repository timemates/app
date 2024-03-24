package org.timemates.app.timers.ui.settings

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Intent
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.State
import org.timemates.app.users.usecases.TimerSettingsUseCase
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.types.value.TimerId
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TimerSettingsReducerTest {
    private val timerId: TimerId = mockk(relaxed = true)
    private val timerSettingsUseCase: TimerSettingsUseCase = mockk()
    private val timerNameValidator: TimerNameValidator = mockk()
    private val timerDescriptionValidator: TimerDescriptionValidator = mockk()

    private val validName = "Learning English"
    private val validDescription = "Timer for group English learning"
    private val invalidName = "Le"
    private val invalidDescription = ""
    private val workTime: Duration = 5.minutes
    private val restTime: Duration = 1.minutes
    private val bigRestEnabled: Boolean = true
    private val bigRestPer: Count = Count.factory.createOrThrow(2)
    private val bigRestTime: Duration = 6.minutes
    private val isEveryoneCanPause: Boolean = true
    private val isConfirmationRequired: Boolean = false
    private val scope = TestScope()

    private val reducer = TimerSettingsReducer(
        timerId = timerId,
        timerSettingsUseCase = timerSettingsUseCase,
        timerNameValidator = timerNameValidator,
        timerDescriptionValidator = timerDescriptionValidator,
    )

    @Test
    fun `OnDoneClicked with valid name should set appropriate state`() {
        // GIVEN
        every { timerNameValidator.validate(any()) } returns
            TimerNameValidator.Result.Success
        every { timerDescriptionValidator.validate(any()) } returns
            TimerDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(
            state = State(name = validName, description = validDescription),
            event = Intent.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                description = validDescription,
                isNameSizeInvalid = false,
                isDescriptionSizeInvalid = false,
                isLoading = true,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnDoneClicked with valid description should set appropriate state`() {
        // GIVEN
        every { timerNameValidator.validate(any()) } returns
            TimerNameValidator.Result.Success
        every { timerDescriptionValidator.validate(any()) } returns
            TimerDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(
            state = State(name = validName, description = validDescription),
            event = Intent.OnDoneClicked,
            machineScope = scope
        ) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                description = validDescription,
                isNameSizeInvalid = false,
                isDescriptionSizeInvalid = false,
                isLoading = true,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnDoneClicked with invalid name should set appropriate state`() {
        // GIVEN
        every { timerNameValidator.validate(any()) } returns
            TimerNameValidator.Result.SizeViolation
        every { timerDescriptionValidator.validate(any()) } returns
            TimerDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(
            state = State(name = invalidName, description = validDescription),
            event = Intent.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = State(
                name = invalidName,
                description = validDescription,
                isNameSizeInvalid = true,
                isDescriptionSizeInvalid = false,
                isLoading = false,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnDoneClicked with invalid description should set appropriate state`() {
        // GIVEN
        every { timerNameValidator.validate(any()) } returns
            TimerNameValidator.Result.Success
        every { timerDescriptionValidator.validate(any()) } returns
            TimerDescriptionValidator.Result.SizeViolation

        // WHEN
        val result = reducer.reduce(
            State(name = validName, description = invalidDescription),
            Intent.OnDoneClicked,
            scope,
        ) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                description = invalidDescription,
                isNameSizeInvalid = false,
                isDescriptionSizeInvalid = true,
                isLoading = false,
            ),
            actual = result,
        )
    }

    @Test
    fun `OnDoneClicked with all parameters entered should set appropriate state`() {
        // GIVEN
        every { timerNameValidator.validate(any()) } returns
            TimerNameValidator.Result.Success
        every { timerDescriptionValidator.validate(any()) } returns
            TimerDescriptionValidator.Result.Success

        // WHEN
        val result = reducer.reduce(
            state = State(
                name = validName,
                description = validDescription,
                workTime = workTime,
                restTime = restTime,
                bigRestEnabled = bigRestEnabled,
                bigRestPer = bigRestPer,
                bigRestTime = bigRestTime,
                isEveryoneCanPause = isEveryoneCanPause,
                isConfirmationRequired = isConfirmationRequired
            ),
            event = Intent.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = State(
                name = validName,
                description = validDescription,
                workTime = workTime,
                restTime = restTime,
                bigRestEnabled = bigRestEnabled,
                bigRestPer = bigRestPer,
                bigRestTime = bigRestTime,
                isEveryoneCanPause = isEveryoneCanPause,
                isConfirmationRequired = isConfirmationRequired,
                isNameSizeInvalid = false,
                isDescriptionSizeInvalid = false,
                isLoading = true,
            ),
            actual = result,
        )
    }
}
