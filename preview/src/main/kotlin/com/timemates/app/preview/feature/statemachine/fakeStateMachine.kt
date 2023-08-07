package com.timemates.app.preview.feature.statemachine

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState

internal fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> fakeStateMachine(
    state: TState,
): StateMachine<TState, TEvent, TEffect> {
    val reducer = object : Reducer<TState, TEvent, TEffect> {
        override fun reduce(state: TState, event: TEvent, sendEffect: (TEffect) -> Unit): TState {
            return state
        }
    }

    return object : StateMachine<TState, TEvent, TEffect>(reducer, listOf()) {
        override fun initDefaultState(): TState {
            return state
        }
    }
}