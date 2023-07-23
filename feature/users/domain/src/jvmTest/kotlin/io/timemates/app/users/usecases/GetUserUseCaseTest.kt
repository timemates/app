package io.timemates.app.users.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.timemates.app.users.repositories.UsersRepository
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.NotFoundException
import io.timemates.sdk.files.types.value.FileId
import io.timemates.sdk.users.profile.types.User
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserId
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun `execute with valid userId should return Success result`() {
        // GIVEN
        val userId = UserId.createOrThrow(1)
        val user = User(
            id = userId,
            name = UserName.createOrThrow("John"),
            description = UserDescription.createOrThrow("Description"),
            avatarFileId = FileId.createOrThrow("..."),
            emailAddress = null,
        )
        coEvery { repository.getUser(userId) } returns Result.success(user)

        // WHEN
        val result = runBlocking { useCase.execute(userId) }

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.Success(user),
            actual = result,
        )
    }

    @Test
    fun `execute with NotFoundException should return NotFound result`() {
        // GIVEN
        val userId = UserId.createOrThrow(1)
        coEvery { repository.getUser(userId) } returns Result.failure(NotFoundException("User not found"))

        // WHEN
        val result = runBlocking { useCase.execute(userId) }

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.NotFound,
            actual = result,
        )
    }

    @Test
    fun `execute with other exceptions should return Failure result`() {
        // GIVEN
        val userId = UserId.createOrThrow(1)
        val exception = Exception("Failed to retrieve user")
        coEvery { repository.getUser(userId) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(userId) }

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.Failure(exception),
            actual = result,
        )
    }
}
