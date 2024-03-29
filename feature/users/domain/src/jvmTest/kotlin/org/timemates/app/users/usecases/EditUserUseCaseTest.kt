package org.timemates.app.users.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.timemates.app.users.repositories.AuthorizationsRepository
import org.timemates.app.users.repositories.UsersRepository
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.sdk.users.profile.types.value.UserName
import kotlin.test.Test
import kotlin.test.assertEquals

class EditUserUseCaseTest {

    private val repository: UsersRepository = mockk()
    private val authorizations: AuthorizationsRepository = mockk()
    private val useCase = EditUserUseCase(repository, authorizations)

    @Test
    fun `execute with valid name and description should return Success result`() {
        // GIVEN
        val userId = UserId.factory.createOrThrow(0)
        val name = UserName.factory.createOrThrow("John Doe")
        val description = UserDescription.factory.createOrThrow("Some description")
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
        val userId = UserId.factory.createOrThrow(1)
        val exception = Exception("Failed to edit user")
        coEvery { authorizations.getMe() } returns Result.success(userId)
        coEvery { repository.editUser(null, null) } returns Result.failure(exception)

        // WHEN
        val result = runBlocking { useCase.execute(null, null) }

        // THEN
        assertEquals(EditUserUseCase.Result.Failure(exception), result)
    }
}
