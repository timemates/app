package app.timemate.client.core.domain.test.type.value

import app.timemate.client.core.domain.type.value.TagId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TagIdTest {
    @Test
    fun `valid TagId is created from valid long`() {
        // GIVEN
        val validLong = Random.nextLong(TagId.MINIMAL_VALUE, Long.MAX_VALUE)

        // WHEN
        val tagId = TagId.factory.createOrThrow(validLong)

        // THEN
        assertEquals(validLong, tagId.long)
    }

    @Test
    fun `creation fails for negative long`() {
        // GIVEN
        val negativeLong = TagId.MINIMAL_VALUE - 1

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagId.factory.createOrThrow(negativeLong)
        }
    }

    @Test
    fun `creation succeeds for zero`() {
        // GIVEN
        val zero = TagId.MINIMAL_VALUE

        // WHEN
        val tagId = TagId.factory.createOrThrow(zero)

        // THEN
        assertEquals(zero, tagId.long)
    }
}
