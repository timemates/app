package org.timemates.app.authorization.data

import org.timemates.app.authorization.data.database.AccountDatabaseQueries
import org.timemates.credentials.CredentialsStorage
import org.timemates.sdk.authorization.types.value.AccessHash
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.exceptions.UnauthorizedException
import org.timemates.sdk.common.providers.AccessHashProvider

class DatabaseAccessHashProvider(
    private val localQueries: AccountDatabaseQueries,
    private val credentialsStorage: CredentialsStorage,
) : AccessHashProvider {
    override suspend fun getOrNull(): AccessHash? {
        // TODO cache in memory
        return localQueries.getCurrent().executeAsOneOrNull()
            ?.let {
                AccessHash.createOrThrow(
                    credentialsStorage.getString("access_hash_${it.id}")
                        ?: throw UnauthorizedException("Authorization wasn't saved to system credentials.")
                )
            }
    }
}