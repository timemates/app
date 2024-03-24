package org.timemates.app.authorization.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.foundation.random.nextString
import org.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.exceptions.InternalServerError
import org.timemates.sdk.common.exceptions.TooManyRequestsException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmEmailAuthorizationUseCaseTest {

    private val authorizationsRepository: AuthorizationsRepository = mockk()
    private val useCase = ConfirmEmailAuthorizationUseCase(authorizationsRepository)

    @Test
    fun `execute with valid verification hash and confirmation code should return Success result`() {
        // GIVEN
        val verificationHash = VerificationHash.factory.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.factory.createOrThrow("12345678")
        val authorization: Authorization = mockk()
        val isNewAccount = false
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns
            Result.success(
                ConfirmAuthorizationRequest.Result(
                    isNewAccount, authorization,
                )
            )

        // WHEN
        val result = runBlocking { useCase.execute(verificationHash, confirmationCode) }

        // THEN
        assertEquals(ConfirmEmailAuthorizationUseCase.Result.Success(authorization, isNewAccount), result)
    }

    @Test
    fun `execute with TooManyRequestsException should return AttemptsExceeded result`() {
        // GIVEN
        val verificationHash = VerificationHash.factory.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.factory.createOrThrow("12345678")
        val exception = TooManyRequestsException("Too many requests", cause = null)
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(verificationHash, confirmationCode) }

        // THEN
        assertEquals(ConfirmEmailAuthorizationUseCase.Result.AttemptsExceeded, result)
    }

    @Test
    fun `execute with other exceptions should return Failure result`() {
        // GIVEN
        val verificationHash = VerificationHash.factory.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.factory.createOrThrow("12345678")
        val exception = InternalServerError("Some error", cause = null)
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(verificationHash, confirmationCode) }

        // THEN
        assertEquals(ConfirmEmailAuthorizationUseCase.Result.Failure(exception), result)
    }
}
