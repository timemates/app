package app.timemate.client.tasks.domain.tests.type

import app.timemate.client.tasks.domain.type.value.TaskStatusName
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
}