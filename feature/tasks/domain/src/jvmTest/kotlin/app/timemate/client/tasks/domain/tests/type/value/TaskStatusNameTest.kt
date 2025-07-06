package app.timemate.client.tasks.domain.tests.type.value

import app.timemate.client.tasks.domain.type.value.TaskStatusName
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class TaskStatusNameTest {

    @Test
    fun `valid TaskStatusName is created from allowed string`() {
        // GIVEN
        val validString = "In Progress"

        // WHEN
        val statusName = TaskStatusName.factory.createOrThrow(validString)

        // THEN
        assertEquals(validString, statusName.string)
    }

    @Test
    fun `creation fails for empty string`() {
        // GIVEN
        val emptyString = ""

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskStatusName.factory.createOrThrow(emptyString)
        }
    }

    @Test
    fun `creation fails for string longer than 100 characters`() {
        // GIVEN
        val longString = "a".repeat(101)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskStatusName.factory.createOrThrow(longString)
        }
    }

    @Test
    fun `isBuiltin returns true for built-in status names (case insensitive)`() {
        // GIVEN
        val builtInNames = listOf(
            TaskStatusName.PLANED,
            TaskStatusName.IN_PROGRESS,
            TaskStatusName.PAUSED,
            TaskStatusName.DONE
        )

        // WHEN / THEN
        builtInNames.forEach { builtIn ->
            assertTrue(builtIn.isBuiltin(), "Expected '${builtIn.string}' to be builtin")
            // Check case insensitivity
            assertTrue(TaskStatusName.factory.createOrThrow(builtIn.string.uppercase()).isBuiltin())
            assertTrue(TaskStatusName.factory.createOrThrow(builtIn.string.lowercase()).isBuiltin())
        }
    }

    @Test
    fun `isBuiltin returns false for non built-in status name`() {
        // GIVEN
        val customName = "CustomStatus"
        val taskStatusName = TaskStatusName.factory.createOrThrow(customName)

        // WHEN / THEN
        assertFalse(taskStatusName.isBuiltin())
        assertTrue(taskStatusName.string == customName)
    }

    @Test
    fun `isNotBuiltin returns true only for non built-in status name`() {
        // GIVEN
        val customName = "SomethingElse"
        val builtIn = TaskStatusName.PLANED
        val custom = TaskStatusName.factory.createOrThrow(customName)

        // WHEN / THEN
        assertFalse(builtIn.isNotBuiltin())
        assertTrue(custom.isNotBuiltin())
        assertTrue(custom.string == customName)
    }
}
