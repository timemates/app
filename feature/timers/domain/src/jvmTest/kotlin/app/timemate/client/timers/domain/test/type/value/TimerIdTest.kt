package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.value.TimerId
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import com.y9vad9.ktiny.kotlidator.ValidationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class TimerIdTest {
    @Test
    fun `should create TimerId for valid positive long`() {
        // GIVEN a valid timer id value
        val validId = 42L

        // WHEN creating TimerId from factory
        val result = TimerId.factory.create(validId)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(validId, result.getOrThrow().long)
    }

    @Test
    fun `should create TimerId for exactly MIN_VALUE`() {
        // GIVEN the minimum allowed value
        val validMin = TimerId.MIN_VALUE

        // WHEN creating TimerId from factory
        val result = TimerId.factory.create(validMin)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(validMin, result.getOrThrow().long)
    }

    @Test
    fun `should fail to create TimerId when value is below MIN_VALUE`() {
        // GIVEN an invalid value below the minimum
        val invalidId = TimerId.MIN_VALUE - 1

        // WHEN creating TimerId from factory
        val result = TimerId.factory.create(invalidId)

        // THEN it should fail with MinValueValidationRule.Failure
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<MinValueValidationRule.Failure<Long>>(exception.failure)
    }
}
