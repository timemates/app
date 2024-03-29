package org.timemates.app.users.repositories

import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.users.profile.types.User
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a repository for user-related operations.
 */
interface UsersRepository {
    /**
     * Retrieves a user with the specified ID.
     *
     * @param id The ID of the user to retrieve.
     * @return Flow of the user with the specified ID.
     */
    suspend fun getUser(id: UserId): Flow<Result<User>>

    /**
     * Retrieves a list of users with the specified IDs.
     *
     * @param ids The list of IDs of users to retrieve.
     * @return Flow of the list of users with the specified IDs.
     */
    suspend fun getUsers(ids: List<UserId>): Flow<Result<List<User>>>

    /**
     * Edits a user with the specified name and description.
     *
     * @param name The new name for the user.
     * @param description The new description for the user.
     */
    suspend fun editUser(name: UserName?, description: UserDescription?): Result<Empty>
}
