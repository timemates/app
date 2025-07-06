package app.timemate.client.tasks.domain.tests.type.value

import app.timemate.client.tasks.domain.type.value.TaskId
import com.y9vad9.ktiny.kotlidator.ValidationException
import com.y9vad9.ktiny.kotlidator.createOrThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaskIdTest {

    @Test
    fun `valid TaskId is created from valid long`() {
        // GIVEN
        val validLong = 1L

        // WHEN
        val taskId = TaskId.factory.createOrThrow(validLong)

        // THEN
        assertEquals(validLong, taskId.long)
    }

    @Test
    fun `creation succeeds for zero value`() {
        // GIVEN
        val zeroValue = 0L

        // WHEN
        val taskId = TaskId.factory.createOrThrow(zeroValue)

        // THEN
        assertEquals(zeroValue, taskId.long)
    }

    @Test
    fun `creation fails for negative long`() {
        // GIVEN
        val negativeLong = -1L

        // WHEN / THEN
        assertFailsWith<ValidationException> {
            TaskId.factory.createOrThrow(negativeLong)
        }
    }
}
