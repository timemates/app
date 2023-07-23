package io.timemates.app.authorization.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.foundation.random.nextString
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import io.timemates.sdk.users.profile.types.value.EmailAddress
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthorizeByEmailUseCaseTest {

    private val authorizationsRepository: AuthorizationsRepository = mockk()
    private val useCase = AuthorizeByEmailUseCase(authorizationsRepository)

    @Test
    fun `execute with successful authorization should return Success`() {
        // GIVEN
        val emailAddress = EmailAddress.createOrThrow("test@example.com")
        val verificationHash = VerificationHash.createOrThrow(Random.nextString(VerificationHash.SIZE))
        coEvery { authorizationsRepository.authorize(emailAddress) } returns Result.success(verificationHash)

        // WHEN
        val result = runBlocking { useCase.execute(emailAddress) }

        // THEN
        assertEquals(
            expected = AuthorizeByEmailUseCase.Result.Success(verificationHash),
            actual = result
        )
    }

    @Test
    fun `execute with TooManyRequestsException should return TooManyRequests`() {
        // GIVEN
        val emailAddress = EmailAddress.createOrThrow("test@example.com")
        val exception = TooManyRequestsException("Too many requests")
        coEvery { authorizationsRepository.authorize(emailAddress) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(emailAddress) }

        // THEN
        assertEquals(
            expected = AuthorizeByEmailUseCase.Result.TooManyRequests,
            actual = result
        )
    }

    @Test
    fun `execute with other exception should return Failure`() {
        // GIVEN
        val emailAddress = EmailAddress.createOrThrow("test@example.com")
        val exception = RuntimeException("Something went wrong")
        coEvery { authorizationsRepository.authorize(emailAddress) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(emailAddress) }

        // THEN
        assertEquals(
            expected = AuthorizeByEmailUseCase.Result.Failure(exception),
            actual = result
        )
    }
}
