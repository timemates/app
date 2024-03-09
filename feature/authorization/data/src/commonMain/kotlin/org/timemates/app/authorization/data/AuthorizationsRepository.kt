package org.timemates.app.authorization.data

import org.timemates.app.authorization.data.database.AccountDatabaseQueries
import org.timemates.credentials.CredentialsStorage
import org.timemates.sdk.authorization.email.EmailAuthorizationApi
import org.timemates.sdk.authorization.email.requests.ConfigureNewAccountRequest
import org.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.AuthorizedSessionsApi
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.authorization.sessions.types.value.ApplicationName
import org.timemates.sdk.authorization.sessions.types.value.ClientIpAddress
import org.timemates.sdk.authorization.sessions.types.value.ClientVersion
import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.exceptions.UnsupportedException
import org.timemates.sdk.users.profile.types.value.EmailAddress
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserName
import org.timemates.app.authorization.repositories.AuthorizationsRepository as AuthorizationRepositoryContract

class AuthorizationsRepository(
    private val emailAuthApi: EmailAuthorizationApi,
    private val sessionsApi: AuthorizedSessionsApi,
    private val localQueries: AccountDatabaseQueries,
    private val mapper: DbAuthorizationMapper,
    private val credentialsStorage: CredentialsStorage,
) : AuthorizationRepositoryContract {
    override suspend fun getCurrentAuthorization(): Authorization? {
        return localQueries.getCurrent().executeAsOneOrNull()
            ?.let { mapper.dbToSdkAuthorization(it) }
    }

    override suspend fun tryAuthorization(): Result<Authorization> {
        // TODO
        return Result.failure(UnsupportedException("Try authorization is not supported on server yet."))
    }

    override suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash> {
        return emailAuthApi.authorize(emailAddress, Authorization.Metadata(appName, appVersion, ClientIpAddress.createOrThrow("UNDEFINED")))
    }

    override suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Result> {
        return emailAuthApi.confirm(verificationHash, code).onSuccess {
            if(!it.isNewAccount) {
                val auth = it.authorization!!
                localQueries.add(
                    userId = auth.userId.long,
                    accessHashExpiresAt = auth.accessHash!!.expiresAt.toEpochMilliseconds(),
                    refreshHashExpiresAt = auth.refreshHash!!.expiresAt.toEpochMilliseconds(),
                    generationTime = auth.generationTime.toEpochMilliseconds(),
                    metadataClientName = auth.metadata?.applicationName?.string,
                    metadataClientIpAddress = auth.metadata?.clientIpAddress?.string,
                    metadataClientVersion = auth.metadata?.clientVersion?.double ?: 1.0,
                    id = null,
                )

                val id = localQueries.getCurrent().executeAsOne().id

                credentialsStorage.setString("access_hash_$id", auth.accessHash!!.value.string)
                credentialsStorage.setString("refresh_hash_$id", auth.refreshHash!!.value.string)
            }
        }
    }

    override suspend fun createNewAccount(
        verificationHash: VerificationHash,
        userName: UserName,
        userDescription: UserDescription,
    ): Result<ConfigureNewAccountRequest.Result> {
        return emailAuthApi.createProfile(verificationHash, userName, userDescription)
            .onSuccess { result ->
                with(result.authorization) {
                    localQueries.add(
                        null,
                        accessHashExpiresAt = accessHash!!.expiresAt.toEpochMilliseconds(),
                        refreshHashExpiresAt = refreshHash!!.expiresAt.toEpochMilliseconds(),
                        generationTime = generationTime.toEpochMilliseconds(),
                        metadataClientName = metadata?.applicationName?.string,
                        metadataClientIpAddress = metadata?.clientIpAddress?.string,
                        metadataClientVersion = metadata?.clientVersion!!.double,
                        userId = userId.long,
                    )

                    val id = localQueries.getCurrent().executeAsOne().id

                    credentialsStorage.setString("access_hash_$id", accessHash!!.value.string)
                    credentialsStorage.setString("refresh_hash_$id", refreshHash!!.value.string)
                }
            }

    }


}

private val appName = ApplicationName.createOrThrow("TimeMates App")
private val appVersion = ClientVersion.createOrThrow(1.0)