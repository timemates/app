package com.y9vad9.pomodoro.client.repositories.integration

import com.y9vad9.pomodoro.client.db.AuthorizationQueries
import com.y9vad9.pomodoro.client.exceptions.UnauthorizedException
import com.y9vad9.pomodoro.client.repositories.AuthorizationRepository
import com.y9vad9.pomodoro.sdk.AuthorizedPomodoroClient
import com.y9vad9.pomodoro.sdk.results.RenewTokenResult
import com.y9vad9.pomodoro.sdk.types.value.RefreshToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbAuthorizationRepository(
    private val authorizationQueries: AuthorizationQueries,
    private val pomodoroClient: AuthorizedPomodoroClient
) : AuthorizationRepository {
    override suspend fun isAuthorizationPresent(): Boolean {
        return withContext(Dispatchers.IO) {
            authorizationQueries.getLast().executeAsOneOrNull() != null
        }
    }

    override suspend fun renewAuthorization(): Boolean {
        return withContext(Dispatchers.IO) {
            val current = authorizationQueries.getLast().executeAsOneOrNull() ?: throw UnauthorizedException()
            val result = pomodoroClient.renewToken(RefreshToken(current.refreshToken))
            if(result is RenewTokenResult.Success) {
                authorizationQueries.updateAccessToken(result.accessToken.string, current.id)
                true
            } else false
        }
    }

    override suspend fun removeAuthorization() {
        withContext(Dispatchers.IO) {
            authorizationQueries.deleteAll()
        }
    }
}