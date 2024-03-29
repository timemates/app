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

class GetUserUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun `execute with valid userId should return Success result`(): Unit = runTest {
        // GIVEN
        val userId = UserId.factory.createOrThrow(1)
        val user = User(
            id = userId,
            name = UserName.factory.createOrThrow("John"),
            description = UserDescription.factory.createOrThrow("Description"),
            avatar = null,
            emailAddress = null,
        )
        coEvery { repository.getUser(userId) } returns flowOf(Result.success(user))

        // WHEN
        val result = useCase.execute(userId)

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.Success(user),
            actual = result.first(),
        )
    }

    @Test
    fun `execute with NotFoundException should return NotFound result`(): Unit = runTest {
        // GIVEN
        val userId = UserId.factory.createOrThrow(1)
        coEvery { repository.getUser(userId) } returns flowOf(Result.failure(NotFoundException("User not found")))

        // WHEN
        val result = useCase.execute(userId)

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.NotFound,
            actual = result.first(),
        )

    }

    @Test
    fun `execute with other exceptions should return Failure result`(): Unit = runTest {
        // GIVEN
        val userId = UserId.factory.createOrThrow(1)
        val exception = Exception("Failed to retrieve user")
        coEvery { repository.getUser(userId) } returns flowOf(Result.failure(exception))

        // WHEN
        val result = useCase.execute(userId)

        // THEN
        assertEquals(
            expected = GetUserUseCase.Result.Failure(exception),
            actual = result.first(),
        )
    }
}
