package app.timemate.client.core.domain.test.type

import app.timemate.client.core.domain.type.Tag
import app.timemate.client.core.domain.type.value.TagId
import app.timemate.client.core.domain.type.value.TagName
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock

class TagTest {

    @Test
    fun `rename returns new tag with updated name and same id and time`() {
        // GIVEN
        val originalName = TagName.factory.createOrThrow("Work")
        val newName = TagName.factory.createOrThrow("Focus")
        val tagId = TagId.factory.createOrThrow(1L)
        val createdAt = Clock.System.now()
        val original = Tag(
            id = tagId,
            name = originalName,
            creationTime = createdAt,
        )

        // WHEN
        val renamed = original.rename(newName)

        // THEN
        assertEquals(
            expected = tagId,
            actual = renamed.id,
            message = "ID should remain unchanged after rename",
        )
        assertEquals(
            expected = newName,
            actual = renamed.name,
            message = "Name should be updated to the new value",
        )
        assertEquals(
            expected = createdAt,
            actual = renamed.creationTime,
            message = "Creation time should remain unchanged after rename",
        )
    }
}