package com.timemates.app.preview.feature.statemachine

import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> fakeStateMachine(
    state: TState,
): StateMachine<TState, TEvent, TEffect> {
    return object : StateMachine<TState, TEvent, TEffect> {
        override val state: StateFlow<TState> = MutableStateFlow(state)
        override val effects: ReceiveChannel<TEffect> = Channel()

        override fun dispatchEvent(event: TEvent) {
            // no-op
        }
    }
}