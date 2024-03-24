package org.timemates.app.preview.feature.mvi

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.timemates.app.feature.common.MVI
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.store

fun <TState : MVIState, TIntent : MVIIntent, TAction : MVIAction> fakeMvi(
    state: TState,
): MVI<TState, TIntent, TAction> {
    return object : MVI<TState, TIntent, TAction>, ComponentContext by DefaultComponentContext(LifecycleRegistry(initialState = Lifecycle.State.STARTED)) {
        override val store: Store<TState, TIntent, TAction> = store(initial = state) {}
    }
}