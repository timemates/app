package org.timemates.app.preview.feature.statemachine

import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState

internal fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> fakeStateMachine(
    state: TState,
): StateMachine<TState, TEvent, TEffect> {
    val reducer = object : Reducer<TState, TEvent, TEffect> {
        override fun ReducerScope<TEffect>.reduce(state: TState, event: TEvent): TState {
            return state
        }
    }

    return object : StateMachine<TState, TEvent, TEffect>(state, reducer, listOf()) {}
}