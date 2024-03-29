package org.timemates.app.feature.common.failures

import org.timemates.sdk.common.exceptions.UnauthorizedException

/**
 * Interface for handling authorization failures.
 *
 * This interface is used when a user's authorization has been terminated. It is typically employed in middlewares
 * between the reducer and the state machine.
 */
fun interface OnAuthorizationFailedHandler {
    /**
     * Called when an authorization failure occurs.
     *
     * @param exception The `UnauthorizedException` representing the authorization failure.
     */
    fun onFailed(exception: UnauthorizedException)
}