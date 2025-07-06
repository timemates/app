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
        val validString = "Valid Tag"

        // WHEN
        val tagName = TagName.factory.createOrThrow(validString)

        // THEN
        assertEquals(validString, tagName.string)
    }

    @Test
    fun `creation fails for empty string`() {
        // GIVEN
        val emptyString = ""

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagName.factory.createOrThrow(emptyString)
        }
    }

    @Test
    fun `creation fails for string longer than maximum allowed length`() {
        // GIVEN
        val tooLongString = "a".repeat(51)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagName.factory.createOrThrow(tooLongString)
        }
    }
}