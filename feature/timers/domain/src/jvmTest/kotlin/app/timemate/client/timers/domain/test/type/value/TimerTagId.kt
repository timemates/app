package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.tag.value.TimerTagId
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import kotlin.test.*
import com.y9vad9.ktiny.kotlidator.ValidationException

class TimerTagIdTests {
    @Test
    fun `should fail when value is less than MIN_VALUE`() {
        // GIVEN a value less than the allowed minimum
        val invalidValue = -1L

        // WHEN trying to create a TimerTagId from it
        val result = TimerTagId.factory.create(invalidValue)

        // THEN creation should fail with a ValidationException containing MinValueValidationRule.Failure
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertIs<ValidationException>(exception)
        assertIs<MinValueValidationRule.Failure<Long>>(exception.failure)
    }

    @Test
    fun `should create TimerTagId successfully when value is equal to MIN_VALUE`() {
        // GIVEN a value equal to the minimum allowed
        val value = TimerTagId.MIN_VALUE

        // WHEN creating a TimerTagId from it
        val result = TimerTagId.factory.create(value)

        // THEN creation should succeed and wrap the correct value
        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrThrow().long)
    }

    @Test
    fun `should create TimerTagId successfully when value is greater than MIN_VALUE`() {
        // GIVEN a valid positive value greater than the minimum
        val value = TimerTagId.MIN_VALUE + 1

        // WHEN creating a TimerTagId from it
        val result = TimerTagId.factory.create(value)

        // THEN creation should succeed and wrap the correct value
        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrThrow().long)
    }

    @Test
    fun `should wrap raw value into value object`() {
        // GIVEN a valid raw value
        val raw = 42L

        // WHEN creating TimerTagId
        val result = TimerTagId.factory.create(raw)

        // THEN resulting object should wrap exactly the given value
        assertTrue(result.isSuccess)
        assertEquals(raw, result.getOrThrow().long)
    }

    @Test
    fun `should return failure on extreme negative value`() {
        // GIVEN an extremely negative value
        val raw = Long.MIN_VALUE

        // WHEN creating TimerTagId
        val result = TimerTagId.factory.create(raw)

        // THEN creation should fail with MinValueValidationRule.Failure
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertIs<ValidationException>(exception)
        assertIs<MinValueValidationRule.Failure<Long>>(exception.failure)
    }

    @Test
    fun `should allow Long_MAX_VALUE as valid input`() {
        // GIVEN the maximum possible long value
        val raw = Long.MAX_VALUE

        // WHEN creating TimerTagId
        val result = TimerTagId.factory.create(raw)

        // THEN creation should succeed
        assertTrue(result.isSuccess)
        assertEquals(raw, result.getOrThrow().long)
    }
}
