package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.value.TimerName
import com.y9vad9.ktiny.kotlidator.ValidationException

import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class TimerNameTest {

    @Test
    fun `should create TimerName when input is within valid length`() {
        // GIVEN a valid timer name
        val input = "Valid Timer"

        // WHEN we attempt to create a TimerName using the factory
        val result = TimerName.factory.create(input)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(input, result.getOrThrow().string)
    }

    @Test
    fun `should fail when TimerName is empty`() {
        // GIVEN an invalid timer name that is empty
        val input = ""

        // WHEN we attempt to create a TimerName
        val result = TimerName.factory.create(input)

        // THEN it should fail with StringLengthRangeValidationRule.Failure
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<StringLengthRangeValidationRule.Failure>(exception.failure)
    }

    @Test
    fun `should fail when TimerName is longer than 50 characters`() {
        // GIVEN an invalid timer name that exceeds the maximum allowed length
        val input = "a".repeat(51)

        // WHEN we attempt to create a TimerName
        val result = TimerName.factory.create(input)

        // THEN it should fail with StringLengthRangeValidationRule.Failure
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<StringLengthRangeValidationRule.Failure>(exception.failure)
    }

    @Test
    fun `should create TimerName with exactly 1 character`() {
        // GIVEN a timer name with the minimum valid length
        val input = "a"

        // WHEN we attempt to create a TimerName
        val result = TimerName.factory.create(input)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(input, result.getOrThrow().string)
    }

    @Test
    fun `should create TimerName with exactly 50 characters`() {
        // GIVEN a timer name with the maximum valid length
        val input = "a".repeat(50)

        // WHEN we attempt to create a TimerName
        val result = TimerName.factory.create(input)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(input, result.getOrThrow().string)
    }
}
