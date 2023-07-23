package io.timemates.app.authorization.validation

import kotlin.test.Test
import kotlin.test.assertEquals

class EmailAddressValidatorTest {

    private val validator = EmailAddressValidator()

    @Test
    fun `validate with valid email address should return Success`() {
        // GIVEN
        val emailAddress = "test@example.com"

        // WHEN
        val result = validator.validate(emailAddress)

        // THEN
        assertEquals(
            expected = EmailAddressValidator.Result.Success,
            actual = result
        )
    }

    @Test
    fun `validate with email address of invalid size should return SizeViolation`() {
        // GIVEN
        val emailAddress = "test"

        // WHEN
        val result = validator.validate(emailAddress)

        // THEN
        assertEquals(
            expected = EmailAddressValidator.Result.SizeViolation,
            actual = result,
        )
    }

    @Test
    fun `validate with email address of invalid pattern should return PatternDoesNotMatch`() {
        // GIVEN
        val emailAddress = "test@gmail"

        // WHEN
        val result = validator.validate(emailAddress)

        // THEN
        assertEquals(
            expected = EmailAddressValidator.Result.PatternDoesNotMatch,
            actual = result
        )
    }
}
