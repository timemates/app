package io.timemates.app.users.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.timemates.app.users.repositories.UsersRepository
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.NotFoundException
import io.timemates.sdk.users.profile.types.User
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserId
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUsersUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val useCase = GetUsersUseCase(repository)

    @Test
    fun `execute with valid userIds should return Success result`(): Unit = runTest {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1), UserId.createOrThrow(2))
        val users = listOf(
            User(
                id = userIds[0],
                name = UserName.createOrThrow("John"),
                description = UserDescription.createOrThrow("Description 1"),
                emailAddress = null,
                avatar = null,
            ),
            User(
                id = userIds[1],
                name = UserName.createOrThrow("Jane"),
                description = UserDescription.createOrThrow("Description 2"),
                emailAddress = null,
                avatar = null,
            )
        )
        coEvery { repository.getUsers(userIds) } returns flowOf(Result.success(users))

        // WHEN
        val result = useCase.execute(userIds)

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.Success(users),
            actual = result.first(),
        )

    }

    @Test
    fun `execute with NotFoundException should return NotFound result`(): Unit = runTest {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1))
        coEvery { repository.getUsers(userIds) } returns flowOf(Result.failure(NotFoundException("User not found")))

        // WHEN
        val result = useCase.execute(userIds)

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.NotFound,
            actual = result.first(),
        )
    }

    @Test
    fun `execute with other exceptions should return Failure result`(): Unit = runTest {
        // GIVEN
        val userIds = listOf(UserId.createOrThrow(1))
        val exception = Exception("Failed to retrieve users")
        coEvery { repository.getUsers(userIds) } returns flowOf(Result.failure(exception))

        // WHEN
        val result = useCase.execute(userIds)

        // THEN
        assertEquals(
            expected = GetUsersUseCase.Result.Failure(exception),
            actual = result.first(),
        )
    }
}
