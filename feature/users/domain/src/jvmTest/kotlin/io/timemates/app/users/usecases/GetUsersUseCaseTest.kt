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

class GetUsersUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val useCase = GetUsersUseCase(repository)

    @Test
    fun `execute with valid userIds should return Success result`() {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1), UserId.createOrThrow(2))
        val users = listOf(
            User(
                id = userIds[0],
                name = UserName.createOrThrow("John"),
                description = UserDescription.createOrThrow("Description 1"),
                avatarFileId = FileId.createOrThrow("..."),
                emailAddress = null,
            ),
            User(
                id = userIds[1],
                name = UserName.createOrThrow("Jane"),
                description = UserDescription.createOrThrow("Description 2"),
                avatarFileId = FileId.createOrThrow("..."),
                emailAddress = null,
            )
        )
        coEvery { repository.getUsers(userIds) } returns Result.success(users)

        // WHEN
        val result = runBlocking { useCase.execute(userIds) }

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.Success(users),
            actual = result,
        )
    }

    @Test
    fun `execute with NotFoundException should return NotFound result`() {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1))
        coEvery { repository.getUsers(userIds) } returns Result.failure(NotFoundException("User not found"))

        // WHEN
        val result = runBlocking { useCase.execute(userIds) }

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.NotFound,
            actual = result,
        )
    }

    @Test
    fun `execute with other exceptions should return Failure result`() {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1))
        val exception = Exception("Failed to retrieve users")
        coEvery { repository.getUsers(userIds) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(userIds) }

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.Failure(exception),
            actual = result,
        )
    }
}
