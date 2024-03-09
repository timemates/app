package org.timemates.app.authorization.repositories

import org.timemates.sdk.authorization.email.requests.ConfigureNewAccountRequest
import org.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.users.profile.types.value.EmailAddress
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserName

/**
 * Interface defining the contract for an authorization repository.
 */
interface AuthorizationsRepository {
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
    suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Result>

    /**
     * Creates a new user account with the given verification hash, user name, and user description.
     *
     * @param verificationHash The verification hash used to confirm the user's account.
     * @param userName The user's name for the new account.
     * @param userDescription The description or additional information for the new account.
     * @return A [Result] object representing the result of the account creation process.
     *         It contains either a [ConfigureNewAccountRequest.Result.Success] with the successful result
     *         or a [ConfigureNewAccountRequest.Result.Failure] with the error information.
     */
    suspend fun createNewAccount(
        verificationHash: VerificationHash,
        userName: UserName,
        userDescription: UserDescription,
    ): Result<ConfigureNewAccountRequest.Result>
}
