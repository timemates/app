package app.timemate.client.timers.domain.test.type.settings.value

import app.timemate.client.timers.domain.type.settings.value.PomodoroFocusTime
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class PomodoroFocusTimeTest {

    @Test
    fun `createOrThrow returns valid instance for allowed duration`() {
        // GIVEN
        val validDuration = 10.minutes

        // WHEN
        val result = PomodoroFocusTime.factory.createOrThrow(validDuration)

        // THEN
        assertEquals(
            expected = validDuration,
            actual = result.duration,
            message = "The stored duration should match the input",
        )
    }

    @Test
    fun `createOrThrow throws for duration less than minimum`() {
        // GIVEN
        val invalidDuration = 9.minutes

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroFocusTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `createOrThrow throws for duration greater than maximum`() {
        // GIVEN
        val invalidDuration = 2.hours

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroFocusTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `create returns failure Result for duration less than minimum`() {
        // GIVEN
        val invalidDuration = 5.minutes

        // WHEN
        val result = PomodoroFocusTime.factory.create(invalidDuration)

        // THEN
        assertTrue(result.isFailure, "Result should be failure for invalid duration")
    }

    @Test
    fun `create returns failure Result for duration greater than maximum`() {
        // GIVEN
        val invalidDuration = 3.hours

        // WHEN
        val result = PomodoroFocusTime.factory.create(invalidDuration)

        // THEN
        assertTrue(result.isFailure, "Result should be failure for invalid duration")
    }

    @Test
    fun `create returns success Result for duration within allowed range`() {
        // GIVEN
        val validDuration = 30.minutes

        // WHEN
        val result = PomodoroFocusTime.factory.create(validDuration)

        // THEN
        assertTrue(result.isSuccess, "Result should be success for valid duration")
        assertEquals(validDuration, result.getOrThrow().duration, "The stored duration should match input")
    }
}
