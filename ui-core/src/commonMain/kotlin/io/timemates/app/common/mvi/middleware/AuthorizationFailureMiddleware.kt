package io.timemates.app.common.mvi.middleware

import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.common.exceptions.UnauthorizedException

class AuthorizationFailureMiddleware<TState : UiState, TEffect : UiEffect>(
    private val onAuthorizationFailed: OnAuthorizationFailedHandler,
) : Middleware<TState, TEffect> {
    interface AuthorizationFailureEffect {
        val exception: UnauthorizedException
    }

    override fun onEffect(effect: TEffect, store: StateStore<TState>): TState {
        if (effect is AuthorizationFailureEffect)
            onAuthorizationFailed.onFailed(effect.exception)

        return store.state.value
    }
}