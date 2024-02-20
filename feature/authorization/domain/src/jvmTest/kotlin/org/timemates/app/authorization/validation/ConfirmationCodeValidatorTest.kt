package org.timemates.app.authorization.validation

import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmationCodeValidatorTest {

    private val validator = ConfirmationCodeValidator()

    @Test
    fun `validate with valid confirmation code should return Success`() {
        // GIVEN
        val confirmationCode = "12345678"

        // WHEN
        val result = validator.validate(confirmationCode)

        // THEN
        assertEquals(ConfirmationCodeValidator.Result.Success, result)
    }

    @Test
    fun `validate with confirmation code of incorrect size should return SizeIsInvalid`() {
        // GIVEN
        val confirmationCode = "123"

        // WHEN
        val result = validator.validate(confirmationCode)

        // THEN
        assertEquals(ConfirmationCodeValidator.Result.SizeIsInvalid, result)
    }

    @Test
    fun `validate with blank confirmation code should return SizeIsInvalid`() {
        // GIVEN
        val confirmationCode = ""

        // WHEN
        val result = validator.validate(confirmationCode)

        // THEN
        assertEquals(ConfirmationCodeValidator.Result.SizeIsInvalid, result)
    }
}
