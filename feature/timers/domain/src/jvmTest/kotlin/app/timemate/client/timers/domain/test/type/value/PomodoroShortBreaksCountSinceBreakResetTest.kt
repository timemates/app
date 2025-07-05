package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCountSinceBreakReset
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PomodoroShortBreaksCountSinceBreakResetTest {

    @Test
    fun `createOrThrow returns valid instance for allowed value`() {
        // GIVEN
        val validValue = 3

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.factory.createOrThrow(validValue)

        // THEN
        assertEquals(validValue, result.int, "The stored int value should match the input")
    }

    @Test
    fun `createOrThrow throws for negative value`() {
        // GIVEN
        val invalidValue = -1

        // WHEN & THEN
        assertFailsWith<ValidationException> {
            PomodoroShortBreaksCountSinceBreakReset.Companion.factory.createOrThrow(invalidValue)
        }
    }

    @Test
    fun `create returns failure Result for negative value`() {
        // GIVEN
        val invalidValue = -5

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.Companion.factory.create(invalidValue)

        // THEN
        assertTrue(result.isFailure, "Result should be failure for invalid input")
    }

    @Test
    fun `create returns success Result for zero value`() {
        // GIVEN
        val validValue = 0

        // WHEN
        val result = PomodoroShortBreaksCountSinceBreakReset.Companion.factory.create(validValue)

        // THEN
        assertTrue(result.isSuccess, "Result should be success for valid input")
        assertEquals(validValue, result.getOrThrow().int, "The stored int should be zero")
    }
}
