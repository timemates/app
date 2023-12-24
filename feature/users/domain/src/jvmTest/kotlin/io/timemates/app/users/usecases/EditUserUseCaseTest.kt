package io.timemates.app.users.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.timemates.app.users.repositories.AuthorizationsRepository
import io.timemates.app.users.repositories.UsersRepository
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.types.Empty
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserId
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class EditUserUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val authorizations: AuthorizationsRepository = mockk()
    private val useCase = EditUserUseCase(repository, authorizations)

    @Test
    fun `execute with valid name and description should return Success result`() {
        // GIVEN
        val userId = UserId.createOrThrow(0)
        val name = UserName.createOrThrow("John Doe")
        val description = UserDescription.createOrThrow("Some description")
        coEvery { authorizations.getMe() } returns Result.success(userId)
        coEvery { repository.editUser(name, description) } returns Result.success(Empty)

        // WHEN
        val result = runBlocking { useCase.execute(name, description) }

        // THEN
        assertEquals(EditUserUseCase.Result.Success, result)
    }

    @Test
    fun `execute with editUser() failure should return Failure result`() {
        // GIVEN
        val userId = UserId.createOrThrow(1)
        val exception = Exception("Failed to edit user")
        coEvery { authorizations.getMe() } returns Result.success(userId)
        coEvery { repository.editUser(null, null) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(null, null) }

        // THEN
        assertEquals(EditUserUseCase.Result.Failure(exception), result)
    }
}
