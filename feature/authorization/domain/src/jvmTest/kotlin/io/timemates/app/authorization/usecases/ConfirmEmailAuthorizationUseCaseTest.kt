package io.timemates.app.authorization.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.foundation.random.nextString
import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.InternalServerError
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmEmailAuthorizationUseCaseTest {

    private val authorizationsRepository: AuthorizationsRepository = mockk()
    private val useCase = ConfirmEmailAuthorizationUseCase(authorizationsRepository)

    @Test
    fun `execute with valid verification hash and confirmation code should return Success result`() {
        // GIVEN
        val verificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.createOrThrow("123456")
        val authorization: Authorization = mockk()
        val isNewAccount = false
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns
            Result.success(
                ConfirmAuthorizationRequest.Response(
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
        val verificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.createOrThrow("123456")
        val exception = TooManyRequestsException("Too many requests")
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(verificationHash, confirmationCode) }

        // THEN
        assertEquals(ConfirmEmailAuthorizationUseCase.Result.AttemptsExceeded, result)
    }

    @Test
    fun `execute with other exceptions should return Failure result`() {
        // GIVEN
        val verificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
        val confirmationCode = ConfirmationCode.createOrThrow("123456")
        val exception = InternalServerError("Some error", cause = null)
        coEvery { authorizationsRepository.confirm(verificationHash, confirmationCode) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(verificationHash, confirmationCode) }

        // THEN
        assertEquals(ConfirmEmailAuthorizationUseCase.Result.Failure(exception), result)
    }
}
