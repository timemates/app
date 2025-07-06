package app.timemate.client.timers.domain.test.type.tag.value

import app.timemate.client.timers.domain.type.tag.value.TimerTagId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TimerTagIdTest {

    @Test
    fun `createOrThrow returns valid instance for non-negative value`() {
        // GIVEN
        val validValue = 42L

        // WHEN
        val result = TimerTagId.factory.createOrThrow(validValue)

        // THEN
        assertEquals(
            actual = result.long,
            expected = validValue,
            message = "Stored long value should match input",
        )
    }

    @Test
    fun `createOrThrow throws for negative value`() {
        // GIVEN
        val invalidValue = -1L

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TimerTagId.factory.createOrThrow(invalidValue)
        }
    }

    @Test
    fun `create returns failure Result for negative value`() {
        // GIVEN
        val invalidValue = -5L

        // WHEN
        val result = TimerTagId.factory.create(invalidValue)

        // THEN
        assertTrue(
            actual = result.isFailure,
            message = "Result should be failure for invalid input",
        )
    }

    @Test
    fun `create returns success Result for zero value`() {
        // GIVEN
        val validValue = 0L

        // WHEN
        val result = TimerTagId.factory.create(validValue)

        // THEN
        assertTrue(
            actual = result.isSuccess,
            message = "Result should be success for valid input",
        )
        assertEquals(
            actual = result.getOrThrow().long,
            expected = validValue,
            message = "Stored long value should match input",
        )
    }
}
