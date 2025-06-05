package app.timemate.client.timers.domain.test.type.value

import app.timemate.client.timers.domain.type.value.PomodoroShortBreaksCount
import com.y9vad9.ktiny.kotlidator.rule.MinValueValidationRule
import kotlin.test.*

class PomodoroShortBreaksCountTests {

    @Test
    fun `should create value when input is equal to minimum`() {
        // GIVEN a valid input equal to the minimum allowed value
        val raw = PomodoroShortBreaksCount.MIN_VALUE

        // WHEN creating the value via factory
        val result = PomodoroShortBreaksCount.factory.create(raw)

        // THEN result should be success and contain the expected value
        assertTrue(result.isSuccess)
        assertEquals(raw, result.getOrThrow().int)
    }

    @Test
    fun `should create value when input is greater than minimum`() {
        // GIVEN a valid input above the minimum
        val raw = PomodoroShortBreaksCount.MIN_VALUE + 1

        // WHEN creating the value via factory
        val result = PomodoroShortBreaksCount.factory.create(raw)

        // THEN result should be success and contain the expected value
        assertTrue(result.isSuccess)
        assertEquals(raw, result.getOrThrow().int)
    }

    @Test
    fun `should fail to create value when input is less than minimum`() {
        // GIVEN an invalid input below the minimum
        val raw = PomodoroShortBreaksCount.MIN_VALUE - 1

        // WHEN creating the value via factory
        val result = PomodoroShortBreaksCount.factory.create(raw)

        // THEN result should be failure with MinValueValidationRule.Failure
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<com.y9vad9.ktiny.kotlidator.ValidationException>(exception)
        assertIs<MinValueValidationRule.Failure>(exception.failure)
    }

    @Test
    fun `should create multiple values independently`() {
        // GIVEN several valid raw values
        val rawValues = listOf(0, 1, 5, 100, Int.MAX_VALUE)

        // WHEN creating value objects from them
        val results = rawValues.map { PomodoroShortBreaksCount.factory.create(it) }

        // THEN each should succeed and match original value
        results.forEachIndexed { index, result ->
            assertTrue(result.isSuccess)
            assertEquals(rawValues[index], result.getOrThrow().int)
        }
    }

    @Test
    fun `should not allow negative values`() {
        // GIVEN a range of negative inputs
        val invalidValues = listOf(-1, -10, Int.MIN_VALUE)

        // WHEN trying to create values
        val results = invalidValues.map { PomodoroShortBreaksCount.factory.create(it) }

        // THEN each should fail with MinValueValidationRule.Failure
        results.forEach { result ->
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertIs<com.y9vad9.ktiny.kotlidator.ValidationException>(exception)
            assertIs<MinValueValidationRule.Failure>(exception.failure)
        }
    }
}
