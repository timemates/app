package app.timemate.client.tasks.domain.tests.type.value

import app.timemate.client.tasks.domain.type.value.TaskName
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaskNameTest {

    @Test
    fun `valid TaskName is created from valid string`() {
        // GIVEN
        val validString = "A valid task name"

        // WHEN
        val taskName = TaskName.factory.createOrThrow(validString)

        // THEN
        assertEquals(validString, taskName.string)
    }

    @Test
    fun `creation fails for empty string`() {
        // GIVEN
        val emptyString = ""

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskName.factory.createOrThrow(emptyString)
        }
    }

    @Test
    fun `creation fails for string exceeding max length`() {
        // GIVEN
        val tooLongString = "a".repeat(101)

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskName.factory.createOrThrow(tooLongString)
        }
    }

    @Test
    fun `creation succeeds for string with max allowed length`() {
        // GIVEN
        val maxLengthString = "a".repeat(100)

        // WHEN
        val taskName = TaskName.factory.createOrThrow(maxLengthString)

        // THEN
        assertEquals(maxLengthString, taskName.string)
    }
}
