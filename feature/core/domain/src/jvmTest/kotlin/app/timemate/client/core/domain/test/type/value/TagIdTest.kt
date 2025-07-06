package app.timemate.client.core.domain.test.type.value

import app.timemate.client.core.domain.type.value.TagId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TagIdTest {

    @Test
    fun `valid TagId is created from valid long`() {
        // GIVEN
        val validLong = 123L

        // WHEN
        val tagId = TagId.factory.createOrThrow(validLong)

        // THEN
        assertEquals(validLong, tagId.long)
    }

    @Test
    fun `creation fails for negative long`() {
        // GIVEN
        val negativeLong = -1L

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagId.factory.createOrThrow(negativeLong)
        }
    }

    @Test
    fun `creation succeeds for zero`() {
        // GIVEN
        val zero = 0L

        // WHEN
        val tagId = TagId.factory.createOrThrow(zero)

        // THEN
        assertEquals(zero, tagId.long)
    }
}