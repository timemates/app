package org.timemates.app.authorization.data

import kotlinx.datetime.Instant
import org.timemates.credentials.CredentialsStorage
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.authorization.types.value.HashValue
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.exceptions.UnauthorizedException
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.app.authorization.data.database.Authorization as DbAuthorization

class DbAuthorizationMapper(
    private val credentialsStorage: CredentialsStorage,
) {
    fun dbToSdkAuthorization(
        dbAuthorization: DbAuthorization,
    ): Authorization = with(dbAuthorization) {
        return@with Authorization(
            accessHash = Authorization.Hash(
                HashValue.factory.createOrThrow(credentialsStorage.getString("access_hash_$id")
                    ?: throw UnauthorizedException("Authorization wasn't saved to system credentials.")),
                Instant.fromEpochMilliseconds(accessHashExpiresAt),
            ),
            refreshHash = Authorization.Hash(
                HashValue.factory.createOrThrow(credentialsStorage.getString("refresh_hash_$id")
                    ?: throw UnauthorizedException("Authorization wasn't saved to system credentials.")),
                Instant.fromEpochMilliseconds(accessHashExpiresAt),
            ),
            generationTime = Instant.fromEpochMilliseconds(generationTime),
            userId = UserId.factory.createOrThrow(userId),
            metadata = null, // TODO when metadata will be implemented on server
        )
    }
}