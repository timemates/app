package app.timemate.client.timers.domain.test.type.settings.value

import app.timemate.client.timers.domain.type.settings.value.PomodoroPreparationTime
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PomodoroPreparationTimeTest {

    @Test
    fun `createOrThrow returns valid instance for allowed duration`() {
        // GIVEN
        val validDuration: Duration = 30.seconds

        // WHEN
        val result = PomodoroPreparationTime.factory.createOrThrow(validDuration)

        // THEN
        assertEquals(
            actual = result.duration,
            expected = validDuration,
            message = "The stored duration should match the input",
        )
    }

    @Test
    fun `createOrThrow throws for duration less than minimum`() {
        // GIVEN
        val invalidDuration: Duration = 3.seconds

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroPreparationTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `createOrThrow throws for duration greater than maximum`() {
        // GIVEN
        val invalidDuration: Duration = 6.minutes

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroPreparationTime.factory.createOrThrow(invalidDuration)
        }
    }

    @Test
    fun `create returns failure Result for duration less than minimum`() {
        // GIVEN
        val invalidDuration: Duration = 4.seconds

        // WHEN
        val result = PomodoroPreparationTime.factory.create(invalidDuration)

        // THEN
        assertTrue(
            actual = result.isFailure,
            message = "Result should be failure for invalid duration",
        )
    }

    @Test
    fun `create returns failure Result for duration greater than maximum`() {
        // GIVEN
        val invalidDuration: Duration = 10.minutes

        // WHEN
        val result = PomodoroPreparationTime.factory.create(invalidDuration)

        // THEN
        assertTrue(
            actual = result.isFailure,
            message = "Result should be failure for invalid duration",
        )
    }

    @Test
    fun `create returns success Result for duration within allowed range`() {
        // GIVEN
        val validDuration: Duration = 1.minutes

        // WHEN
        val result = PomodoroPreparationTime.factory.create(validDuration)

        // THEN
        assertTrue(
            actual = result.isSuccess,
            message = "Result should be success for valid duration",
        )
        assertEquals(
            actual = result.getOrThrow().duration,
            expected = validDuration,
            message = "The stored duration should match input",
        )
    }
}