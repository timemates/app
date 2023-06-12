package io.timemates.app.common.mvi.middleware

import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.common.mvi.Middleware
import io.timemates.common.mvi.StateStore
import io.timemates.common.mvi.UiEffect
import io.timemates.common.mvi.UiState
import io.timemates.sdk.common.exceptions.UnauthorizedException

class AuthorizationFailureMiddleware<TState : UiState, TEffect : UiEffect>(
    private val onAuthorizationFailed: OnAuthorizationFailedHandler,
) : Middleware<TState, TEffect> {
    interface AuthorizationFailureEffect {
        val exception: UnauthorizedException
    }

    override fun onEffect(effect: TEffect, store: StateStore<TState>, setState: (TState) -> Unit) {
        if(effect is AuthorizationFailureEffect)
            onAuthorizationFailed.onFailed(effect.exception)
    }
}