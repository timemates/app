package app.timemate.client.timers.domain.test.type.tag.value

import app.timemate.client.timers.domain.type.tag.value.TimerTagName
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TimerTagNameTest {

    @Test
    fun `createOrThrow returns valid instance for allowed string`() {
        // GIVEN
        val validString = "Productive Tag"

        // WHEN
        val result = TimerTagName.factory.createOrThrow(validString)

        // THEN
        assertEquals(
            actual = result.string,
            expected = validString,
            message = "Stored string should match the input string",
        )
    }

    @Test
    fun `createOrThrow throws for empty string`() {
        // GIVEN
        val empty = ""

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TimerTagName.factory.createOrThrow(empty)
        }
    }

    @Test
    fun `createOrThrow throws for string exceeding max length`() {
        // GIVEN
        val tooLong = "a".repeat(51)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TimerTagName.factory.createOrThrow(tooLong)
        }
    }

    @Test
    fun `create returns failure for invalid string`() {
        // GIVEN
        val invalid = ""

        // WHEN
        val result = TimerTagName.factory.create(invalid)

        // THEN
        assertTrue(
            actual = result.isFailure,
            message = "Result should be failure for invalid string",
        )
    }

    @Test
    fun `create returns success for valid string`() {
        // GIVEN
        val valid = "Focused"

        // WHEN
        val result = TimerTagName.factory.create(valid)

        // THEN
        assertTrue(
            actual = result.isSuccess,
            message = "Result should be success for valid input",
        )
        assertEquals(
            actual = result.getOrThrow().string,
            expected = valid,
            message = "Stored string should match input",
        )
    }
}
