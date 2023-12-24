package io.timemates.app.authorization.data

import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.types.value.HashValue
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.users.profile.types.value.UserId
import kotlinx.datetime.Instant
import io.timemates.app.authorization.data.database.Authorization as DbAuthorization

class DbAuthorizationMapper {
    fun dbToSdkAuthorization(dbAuthorization: DbAuthorization): Authorization = with(dbAuthorization) {
        return@with Authorization(
            accessHash = Authorization.Hash(
                HashValue.createOrThrow(accessHashValue),
                Instant.fromEpochMilliseconds(accessHashExpiresAt),
            ),
            refreshHash = Authorization.Hash(
                HashValue.createOrThrow(accessHashValue),
                Instant.fromEpochMilliseconds(accessHashExpiresAt),
            ),
            generationTime = Instant.fromEpochMilliseconds(generationTime),
            userId = UserId.createOrThrow(userId),
            metadata = null, // TODO when metadata will be implemented on server
        )
    }
}