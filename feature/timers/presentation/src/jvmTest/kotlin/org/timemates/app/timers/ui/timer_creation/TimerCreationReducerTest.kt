package org.timemates.app.timers.ui.timer_creation

import io.mockk.every
import io.mockk.mockk
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.types.value.Count
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.timemates.app.foundation.mvi.reduce
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationReducer
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent
import org.timemates.app.users.usecases.TimerCreationUseCase
import org.timemates.app.users.validation.TimerDescriptionValidator
import org.timemates.app.users.validation.TimerNameValidator
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TimerCreationReducerTest {
    private val timerCreationUseCase: TimerCreationUseCase = mockk()
    private val timerNameValidator: TimerNameValidator = mockk()
    private val timerDescriptionValidator: TimerDescriptionValidator = mockk()

    private val validName = "Learning English"
    private val validDescription = "Timer for group English learning"
    private val invalidName = "Le"
    private val invalidDescription = ""
    private val workTime: Duration = 5.minutes
    private val restTime: Duration = 1.minutes
    private val bigRestEnabled: Boolean = true
    private val bigRestPer: Count = Count.createOrThrow(2)
    private val bigRestTime: Duration = 6.minutes
    private val isEveryoneCanPause: Boolean = true
    private val isConfirmationRequired: Boolean = false
    private val scope = TestScope()

    private val reducer = TimerCreationReducer(
        timerCreationUseCase = timerCreationUseCase,
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
            state = TimerCreationScreenComponent.State(name = validName, description = validDescription),
            event = TimerCreationScreenComponent.Event.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimerCreationScreenComponent.State(
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
            TimerCreationScreenComponent.State(name = validName, description = validDescription),
            TimerCreationScreenComponent.Event.OnDoneClicked,
            scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimerCreationScreenComponent.State(
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
            state = TimerCreationScreenComponent.State(name = invalidName, description = validDescription),
            event = TimerCreationScreenComponent.Event.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimerCreationScreenComponent.State(
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
            state = TimerCreationScreenComponent.State(name = validName, description = invalidDescription),
            event = TimerCreationScreenComponent.Event.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimerCreationScreenComponent.State(
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
            state = TimerCreationScreenComponent.State(
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
            event = TimerCreationScreenComponent.Event.OnDoneClicked,
            machineScope = scope,
        ) {}

        // THEN
        assertEquals(
            expected = TimerCreationScreenComponent.State(
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
