package org.timemates.app.timers.ui.timers_list.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.timemates.app.feature.common.MVI
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Action
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Intent
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.State
import org.timemates.app.users.repositories.TimersRepository
import org.timemates.app.users.usecases.GetUserTimersUseCase
import org.timemates.sdk.common.pagination.PagesIterator
import org.timemates.sdk.timers.types.Timer
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.cache
import pro.respawn.flowmvi.plugins.init
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce

class TimersListScreenComponent(
    componentContext: ComponentContext,
    private val getUserTimersUseCase: GetUserTimersUseCase,
    private val timersRepository: TimersRepository,
) : ComponentContext by componentContext, MVI<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(initial = State(isLoading = true)) {
        recover { exception ->
            action(Action.Failure(exception))
            null
        }

        val pagesIterator by cache {
            when (val result = getUserTimersUseCase.execute()) {
                is GetUserTimersUseCase.Result.Success ->
                    result.list
            }
        }

        init {
            loadTimers(pagesIterator)
        }

        reduce { intent ->
            when (intent) {
                Intent.Load -> {
                    updateState { copy(isLoading = true) }
                    loadTimers(pagesIterator)
                    checkUpdates()
                }
            }
        }
    }

    private fun PipelineContext<State, Intent, Action>.loadTimers(pagesIterator: PagesIterator<Timer>) {
        launch {
            val hasNext = pagesIterator.hasNext()

            if (hasNext) {
                val currentResult = pagesIterator.next()
                currentResult
                    .onSuccess {
                        updateState { copy(timersList = (timersList + currentResult.getOrThrow()).toImmutableList()) }
                    }.onFailure {
                        action(Action.Failure(currentResult.exceptionOrNull()!!))
                    }
            }

            updateState { copy(isLoading = false, hasMoreItems = hasMoreItems) }
        }
    }

    private suspend fun PipelineContext<State, Intent, Action>.checkUpdates() {
        timersRepository.getTimersUpdates().collect { update ->
            updateState {
                when (update) {
                    is TimersRepository.TimerUpdateAction.Added ->
                        copy(timersList = (listOf(update.timer) + timersList).toImmutableList())

                    is TimersRepository.TimerUpdateAction.Deleted ->
                        copy(timersList = timersList.filter { it.timerId != update.timerId }.toImmutableList())

                    is TimersRepository.TimerUpdateAction.Updated ->
                        copy(timersList = timersList.map {
                            if (it.timerId == update.timer.timerId) update.timer else it
                        }.toImmutableList())
                }
            }

        }
    }

    @Immutable
    data class State(
        val timersList: ImmutableList<Timer> = persistentListOf(),
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