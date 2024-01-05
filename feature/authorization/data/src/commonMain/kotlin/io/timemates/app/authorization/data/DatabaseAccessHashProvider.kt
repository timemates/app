package io.timemates.app.authorization.data

import io.timemates.app.authorization.data.database.AccountDatabaseQueries
import io.timemates.sdk.authorization.types.value.AccessHash
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.providers.AccessHashProvider

class DatabaseAccessHashProvider(private val localQueries: AccountDatabaseQueries) : AccessHashProvider {
    override suspend fun getOrNull(): AccessHash? {
        // TODO cache in memory
        return localQueries.getCurrent().executeAsOneOrNull()
            ?.let { AccessHash.createOrThrow(it.accessHashValue) }
    }
}