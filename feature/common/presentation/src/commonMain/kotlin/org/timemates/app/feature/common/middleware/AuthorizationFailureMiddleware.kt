package org.timemates.app.feature.common.middleware

import org.timemates.app.feature.common.handler.OnAuthorizationFailedHandler
import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.foundation.mvi.StateStore
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.common.exceptions.UnauthorizedException

/**
 * Middleware class for handling authorization failure effects.
 *
 * @param onAuthorizationFailed The handler for authorization failure events.
 * @param TState The type of UI state.
 * @param TEffect The type of UI effect.
 */
class AuthorizationFailureMiddleware<TState : UiState, TEffect : UiEffect>(
    private val onAuthorizationFailed: OnAuthorizationFailedHandler,
) : Middleware<TState, TEffect> {
    /**
     * Interface representing an effect indicating an authorization failure.
     * UI effects from different screens should implement this interface
     * to support the AuthorizationFailureMiddleware.
     */
    interface AuthorizationFailureEffect : UiEffect {
        /**
         * The unauthorized exception associated with the authorization failure.
         */
        val exception: UnauthorizedException
    }

    /**
     * Handles the [AuthorizationFailureEffect] effect and performs the necessary actions in response to an authorization failure.
     *
     * @param effect The UI effect to handle.
     * @param store The state store to access the current state.
     * @return The updated UI state after handling the effect.
     */
    override fun onEffect(effect: TEffect, state: TState): TState {
        if (effect is AuthorizationFailureEffect)
            onAuthorizationFailed.onFailed(effect.exception)

        return state
    }
}
