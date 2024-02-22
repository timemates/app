package org.timemates.app.foundation.mvi

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.coroutines.CoroutineContext

/**
 * Creates and initializes an instance of an MVI (Model-View-Intent) component using class delegation.
 *
 * @param componentContext The context for the component, typically obtained from the Decompose library.
 * @param initState The initial state of the UI.
 * @param reducer An implementation of the Reducer interface responsible for handling state updates
 * based on UI events.
 * @param middlewares Optional list of Middleware instances to handle asynchronous tasks and state
 * modifications triggered by UI effects.
 * @return An instance of [MVI] representing the MVI component.
 *
 * @param TState The type representing the UI state.
 * @param TEvent The type representing UI events.
 * @param TEffect The type representing UI effects.
 */
public inline fun <reified TState : UiState, TEvent : UiEvent, TEffect : UiEffect> mviComponent(
    componentContext: ComponentContext,
    componentName: String,
    initState: TState,
    reducer: Reducer<TState, TEvent, TEffect>,
    middlewares: List<Middleware<TState, TEffect>> = emptyList(),
): MVIComponent<TState, TEvent, TEffect> {
    return MVIComponent(
        componentContext = componentContext,
        componentName = componentName,
        initState = initState,
        stateSerializer = serializer(),
        reducer = reducer,
        middlewares = middlewares,
    )
}

public fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> MVIComponent(
    componentContext: ComponentContext,
    componentName: String,
    initState: TState,
    stateSerializer: KSerializer<TState>,
    reducer: Reducer<TState, TEvent, TEffect>,
    middlewares: List<Middleware<TState, TEffect>> = emptyList(),
): MVIComponent<TState, TEvent, TEffect> {
    return MVIComponentImpl(componentContext, componentName, initState, stateSerializer, reducer, middlewares)
}

public interface MVIComponent<TState : UiState, TEvent : UiEvent, TEffect : UiEffect> : MVI<TState, TEvent, TEffect>, ComponentContext {
    public val componentName: String
}

/**
 * The base abstract class for implementing a component in the Model-View-Intent (MVI) architecture
 * using the Decompose library. This component manages the UI state, handles UI events, and emits
 * UI effects in a structured and reactive manner.
 *
 * @param TState The type representing the UI state.
 * @param TEvent The type representing UI events.
 * @param TEffect The type representing effects to UI.
 * @param componentContext The context for the component, typically obtained from the Decompose library.
 * @param componentName The name of component that is used to retain state (like an identifier).
 * @param initState The initial state of the UI.
 * @property reducer An implementation of the Reducer interface responsible for handling state updates
 * based on UI events.
 * @property middlewares Optional list of Middleware instances to handle asynchronous tasks and state
 * modifications triggered by UI effects.
 */
private class MVIComponentImpl<TState : UiState, TEvent : UiEvent, TEffect : UiEffect>(
    private val componentContext: ComponentContext,
    override val componentName: String,
    initState: TState,
    private val stateSerializer: KSerializer<TState>,
    private val reducer: Reducer<TState, TEvent, TEffect>,
    private val middlewares: List<Middleware<TState, TEffect>> = emptyList(),
) : MVIComponent<TState, TEvent, TEffect>, ComponentContext by componentContext {

    private val _state: MutableStateFlow<TState> by lazy {
        MutableStateFlow(stateKeeper.consume(componentName, stateSerializer) ?: initState)
    }
    private val _effects: Channel<TEffect> = Channel(Channel.UNLIMITED)

    private val coroutineScope: CoroutineScope = coroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        componentContext.stateKeeper.register(componentName, stateSerializer, _state::value)
    }

    /**
     * Represents the current state of the UI.
     */
    override val state: StateFlow<TState> by ::_state

    /**
     * Represents the channel for emitting UI effects.
     */
    override val effects: ReceiveChannel<TEffect> by ::_effects

    private val sendEffect: (TEffect) -> Unit = { effect ->
        middlewares.forEach { middleware -> updateState { middleware.onEffect(effect, it) } }
        _effects.trySend(effect)
    }

    private val reducerScope = ReducerScope(sendEffect, coroutineScope)

    /**
     * Processes an event from UI.
     *
     * @param event The event to be processed.
     */
    override fun dispatchEvent(event: TEvent) {
        with(reducer) {
            updateState { reducerScope.reduce(it, event) }
        }
    }

    /**
     * A helper method to safely update the UI state. It takes a lambda that defines the state update logic.
     *
     * @param action A lambda function that defines how to update the state based on the current state.
     */
    private fun updateState(action: (TState) -> TState) {
        _state.update { action(it) }
    }
}

private fun ComponentContext.coroutineScope(context: CoroutineContext): CoroutineScope {
    val coroutineScope = CoroutineScope(context)
    lifecycle.doOnDestroy(coroutineScope::cancel)
    return coroutineScope
}