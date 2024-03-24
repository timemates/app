package org.timemates.app.users.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.timemates.app.users.repositories.UsersRepository
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.exceptions.NotFoundException
import org.timemates.sdk.users.profile.types.User
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.sdk.users.profile.types.value.UserName
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUsersUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val useCase = GetUsersUseCase(repository)

    @Test
    fun `execute with valid userIds should return Success result`(): Unit = runTest {
        // GIVEN
        val userIds = listOf(UserId.factory.createOrThrow(1), UserId.factory.createOrThrow(2))
        val users = listOf(
            User(
                id = userIds[0],
                name = UserName.factory.createOrThrow("John"),
                description = UserDescription.factory.createOrThrow("Description 1"),
                emailAddress = null,
                avatar = null,
            ),
            User(
                id = userIds[1],
                name = UserName.factory.createOrThrow("Jane"),
                description = UserDescription.factory.createOrThrow("Description 2"),
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
        val userIds = listOf(UserId.factory.createOrThrow(1))
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
        val userIds = listOf(UserId.factory.createOrThrow(1))
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
