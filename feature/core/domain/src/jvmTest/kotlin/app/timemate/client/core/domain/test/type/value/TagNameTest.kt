package app.timemate.client.core.domain.test.type.value

import app.timemate.client.core.domain.type.value.TagName
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TagNameTest {
    @Test
    fun `valid TagName is created from valid string`() {
        // GIVEN
        val validLength = TagName.LENGTH_RANGE.random()
        val validString = "a".repeat(validLength)

        // WHEN
        val tagName = TagName.factory.createOrThrow(validString)

        // THEN
        assertEquals(validString, tagName.string)
    }

    @Test
    fun `creation fails for less than required minimal length`() {
        // GIVEN
        val string = "a".repeat(TagName.MIN_LENGTH - 1)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagName.factory.createOrThrow(string)
        }
    }

    @Test
    fun `creation fails for string longer than maximum allowed length`() {
        // GIVEN
        val tooLongString = "a".repeat(TagName.MAX_LENGTH + 1)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagName.factory.createOrThrow(tooLongString)
        }
    }

    @Test
    fun `creation succeeds for string with minimum length`() {
        // GIVEN
        val minLengthString = "a".repeat(TagName.MIN_LENGTH)

        // WHEN
        val tagName = TagName.factory.createOrThrow(minLengthString)

        // THEN
        assertEquals(minLengthString, tagName.string)
    }

    @Test
    fun `creation succeeds for string with maximum length`() {
        // GIVEN
        val maxLengthString = "a".repeat(TagName.MAX_LENGTH)

        // WHEN
        val tagName = TagName.factory.createOrThrow(maxLengthString)

        // THEN
        assertEquals(maxLengthString, tagName.string)
    }
}
