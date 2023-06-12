package io.timemates.app.core.repositories

import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.users.profile.types.value.EmailAddress

/**
 * Interface defining the contract for an authorization repository.
 */
interface AuthorizationRepository {
    /**
     * Retrieves the current authorization details if authorized.
     *
     * @return The current authorization, or null if not available.
     */
    suspend fun getCurrentAuthorization(): Authorization?

    /**
     * Requests to server validation of the authorization.
     *
     * @return [getCurrentAuthorization] if authorization is valid on server.
     */
    suspend fun tryAuthorization(): Result<Authorization>

    /**
     * Authorizes the given email address.
     *
     * @param emailAddress The email address to be authorized.
     * @return A [Result] indicating the success or failure of the authorization request,
     *         with the verification hash as the successful result.
     */
    suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash>

    /**
     * Confirms the authorization via email using the provided verification hash from [authorize]
     * and confirmation code from email.
     *
     * @param verificationHash The verification hash associated with the authorization request.
     * @param code The confirmation code to be used for confirmation.
     * @return A [Result] indicating the success or failure of the confirmation request,
     *         with the response from the confirmation as the successful result.
     */
    suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Response>
}
