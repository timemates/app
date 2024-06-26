package org.timemates.app.timers.ui.timers_list.mvi

import androidx.compose.runtime.Immutable
import app.cash.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.timemates.app.feature.common.MVI
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Action
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Intent
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.State
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.sdk.timers.types.Timer
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.recover

class TimersListScreenComponent(
    componentContext: ComponentContext,
    private val timersRepository: TimersRepository,
) : ComponentContext by componentContext, MVI<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(
        initial = State(isLoading = true, timersList = timersRepository.getUserTimers()),
    ) {
        recover { exception ->
            action(Action.Failure(exception))
            null
        }

//        val pages = timersRepository.getUserTimers()
//
////        init {
////            timersRepository.getUserTimers().collect {
////                updateState { copy(timersList = it) }
////            }
////        }
    }

//    private fun PipelineContext<State, Intent, Action>.loadTimers(pagesIterator: PagesIterator<Timer>) {
//        launch {
//            val hasNext = pagesIterator.hasNext()
//
//            if (hasNext) {
//                val currentResult = pagesIterator.next()
//                currentResult
//                    .onSuccess {
//                        updateState { copy(timersList = (timersList + currentResult.getOrThrow()).toImmutableList()) }
//                    }.onFailure {
//                        action(Action.Failure(currentResult.exceptionOrNull()!!))
//                    }
//            }
//
//            updateState { copy(isLoading = false, hasMoreItems = hasMoreItems) }
//        }
//    }

    @Immutable
    data class State(
        val timersList: Flow<PagingData<Timer>>,
        val hasMoreItems: Boolean = true,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    ) : MVIState

    sealed class Intent : MVIIntent {
        data object Load : Intent()
    }

    sealed class Action : MVIAction {
        data class Failure(val throwable: Throwable) : Action()
    }
}