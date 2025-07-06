package app.timemate.client.timers.domain.test.type.settings.value

import app.timemate.client.timers.domain.type.settings.value.PomodoroLongBreakTime
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class PomodoroLongBreakTimeTest {

    @Test
    fun `createOrThrow returns valid instance for allowed duration`() {
        // GIVEN
        val validDuration = 3.minutes

        // WHEN
        val result = PomodoroLongBreakTime.factory.createOrThrow(validDuration)

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
        val invalidDuration = 2.minutes

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroLongBreakTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `createOrThrow throws for duration greater than maximum`() {
        // GIVEN
        val invalidDuration = 2.hours

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroLongBreakTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `create returns failure Result for duration less than minimum`() {
        // GIVEN
        val invalidDuration = 1.minutes

        // WHEN
        val result = PomodoroLongBreakTime.factory.create(invalidDuration)

        // THEN
        assertTrue(result.isFailure, "Result should be failure for invalid duration")
    }

    @Test
    fun `create returns failure Result for duration greater than maximum`() {
        // GIVEN
        val invalidDuration = 3.hours

        // WHEN
        val result = PomodoroLongBreakTime.factory.create(invalidDuration)

        // THEN
        assertTrue(result.isFailure, "Result should be failure for invalid duration")
    }

    @Test
    fun `create returns success Result for duration within allowed range`() {
        // GIVEN
        val validDuration = 30.minutes

        // WHEN
        val result = PomodoroLongBreakTime.factory.create(validDuration)

        // THEN
        assertTrue(
            actual = result.isSuccess,
            message = "Result should be success for valid duration",
        )
        assertEquals(
            expected = validDuration,
            actual = result.getOrThrow().duration,
            message = "The stored duration should match input",
        )
    }
}
