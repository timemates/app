package app.timemate.client.tasks.domain.tests.type.value

import app.timemate.client.tasks.domain.type.value.TaskStatusId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class TaskStatusIdTest {

    @Test
    fun `valid TaskStatusId is created from allowed value`() {
        // GIVEN
        val validValue = -1L

        // WHEN
        val statusId = TaskStatusId.factory.createOrThrow(validValue)

        // THEN
        assertEquals(validValue, statusId.long)
    }

    @Test
    fun `creation fails for value below minimum`() {
        // GIVEN
        val invalidValue = -5L

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskStatusId.factory.createOrThrow(invalidValue)
        }
    }

    @Test
    fun `predefined constants have expected values`() {
        // GIVEN / WHEN / THEN
        assertEquals(-4L, TaskStatusId.PLANED.long)
        assertEquals(-3L, TaskStatusId.IN_PROGRESS.long)
        assertEquals(-2L, TaskStatusId.PAUSED.long)
        assertEquals(-1L, TaskStatusId.DONE.long)
    }

    @Test
    fun `isBuiltin returns true for built-in TaskStatusIds`() {
        // GIVEN
        val builtIns = listOf(
            TaskStatusId.PLANED,
            TaskStatusId.IN_PROGRESS,
            TaskStatusId.PAUSED,
            TaskStatusId.DONE
        )

        // WHEN / THEN
        builtIns.forEach { id ->
            assertTrue(id.isBuiltin(), "Expected $id to be builtin")
        }
    }

    @Test
    fun `isBuiltin returns false for non built-in TaskStatusId`() {
        // GIVEN
        val customId = TaskStatusId.factory.createOrThrow(0L)

        // WHEN / THEN
        assertFalse(customId.isBuiltin())
    }

    @Test
    fun `isNotBuiltin returns true for non built-in TaskStatusId`() {
        // GIVEN
        val customId = TaskStatusId.factory.createOrThrow(0L)

        // WHEN / THEN
        assertTrue(customId.isNotBuiltin())
    }

    @Test
    fun `isNotBuiltin returns false for built-in TaskStatusId`() {
        // GIVEN
        val builtIn = TaskStatusId.PLANED

        // WHEN / THEN
        assertFalse(builtIn.isNotBuiltin())
    }
}
