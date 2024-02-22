package org.timemates.app.preview.feature.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.timemates.app.foundation.mvi.MVI
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState

fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> fakeMvi(
    state: TState,
): MVI<TState, TEvent, TEffect> {
    return object : MVI<TState, TEvent, TEffect> {
        override val effects: ReceiveChannel<TEffect> = Channel()
        override val state: StateFlow<TState> = MutableStateFlow(state)

        override fun dispatchEvent(event: TEvent) {
            // no-op
        }
    }
}