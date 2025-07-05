package app.timemate.client.tasks.domain.tests.type

import app.timemate.client.tasks.domain.type.value.TaskDescription
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaskDescriptionTest {

    @Test
    fun `valid TaskDescription is created from valid string`() {
        // GIVEN
        val validString = "Valid description"

        // WHEN
        val taskDescription = TaskDescription.factory.createOrThrow(validString)

        // THEN
        assertEquals(validString, taskDescription.string)
    }

    @Test
    fun `creation fails for empty string`() {
        // GIVEN
        val emptyString = ""

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskDescription.factory.createOrThrow(emptyString)
        }
    }

    @Test
    fun `creation fails for string longer than maximum allowed length`() {
        // GIVEN
        val tooLongString = "a".repeat(10_001)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskDescription.factory.createOrThrow(tooLongString)
        }
    }
}