package app.timemate.client.core.domain.test.type.value

import app.timemate.client.core.domain.type.value.TagId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TagIdTest {
    private companion object {
        private const val MIN_ID_VALUE: Long = TagId.MINIMAL_VALUE
    }

    @Test
    fun `valid TagId is created from valid long`() {
        // GIVEN
        val validLong = Random.nextLong(MIN_ID_VALUE, Long.MAX_VALUE)

        // WHEN
        val tagId = TagId.factory.createOrThrow(validLong)

        // THEN
        assertEquals(validLong, tagId.long)
    }

    @Test
    fun `creation fails for negative long`() {
        // GIVEN
        val negativeLong = MIN_ID_VALUE - 1

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TagId.factory.createOrThrow(negativeLong)
        }
    }

    @Test
    fun `creation succeeds for zero`() {
        // GIVEN
        val zero = MIN_ID_VALUE

        // WHEN
        val tagId = TagId.factory.createOrThrow(zero)

        // THEN
        assertEquals(zero, tagId.long)
    }
}
