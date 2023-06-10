package io.timemates.app.core.repositories

import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.users.profile.types.value.EmailAddress

interface AuthorizationRepository {
    suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash>

    suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Response>
}