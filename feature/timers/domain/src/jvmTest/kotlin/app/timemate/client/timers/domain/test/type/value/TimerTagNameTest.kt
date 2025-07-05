package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.tag.value.TimerTagName

import com.y9vad9.ktiny.kotlidator.rule.StringLengthRangeValidationRule
import kotlin.test.*
import com.y9vad9.ktiny.kotlidator.ValidationException

class TimerTagNameTests {

    // GIVEN a TimerTagName factory with 1..50 length constraint
    private val factory = TimerTagName.factory

    @Test
    fun `should create tag with length exactly 1`() {
        // WHEN creating a tag with exactly 1 character
        val result = factory.create("A")

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals("A", result.getOrThrow().string)
    }

    @Test
    fun `should create tag with length exactly 50`() {
        // WHEN creating a tag with exactly 50 characters
        val input = "A".repeat(50)
        val result = factory.create(input)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(input, result.getOrThrow().string)
    }

    @Test
    fun `should fail to create tag with length 0`() {
        // WHEN creating a tag with empty string
        val result = factory.create("")

        // THEN it should fail with StringLengthRangeValidationRule.Failure
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<StringLengthRangeValidationRule.Failure>(exception!!.failure)
    }

    @Test
    fun `should fail to create tag with length greater than 50`() {
        // WHEN creating a tag with 51 characters
        val input = "A".repeat(51)
        val result = factory.create(input)

        // THEN it should fail with StringLengthRangeValidationRule.Failure
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<StringLengthRangeValidationRule.Failure>(exception!!.failure)
    }

    @Test
    fun `should fail to create tag with only whitespaces if length is invalid`() {
        // WHEN creating a tag with 0 spaces
        val result = factory.create(" ".repeat(0))

        // THEN it should fail due to length check
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertIs<StringLengthRangeValidationRule.Failure>(exception!!.failure)
    }

    @Test
    fun `should create tag with 1 space character`() {
        // WHEN creating a tag with single whitespace
        val result = factory.create(" ")

        // THEN it should succeed because length is 1 and spaces are counted
        assertTrue(result.isSuccess)
        assertEquals(" ", result.getOrThrow().string)
    }

    @Test
    fun `should create tag with alphanumeric and symbols within limit`() {
        // WHEN creating a valid complex tag
        val input = "Task#42: Übung_@home!"
        val result = factory.create(input)

        // THEN it should succeed
        assertTrue(result.isSuccess)
        assertEquals(input, result.getOrThrow().string)
    }
}
