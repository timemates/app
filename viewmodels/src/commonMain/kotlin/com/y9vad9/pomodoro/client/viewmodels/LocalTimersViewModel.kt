package com.y9vad9.pomodoro.client.viewmodels

import com.y9vad9.pomodoro.client.types.LocalTimer
import com.y9vad9.pomodoro.client.usecases.timers.local.GetLocalTimersUseCase
import com.y9vad9.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalTimersViewModel(
    private val getLocalTimers: GetLocalTimersUseCase
) : ViewModel() {
    val isLoading: StateFlow<Boolean> by ::_isLoading
    val timers: StateFlow<List<LocalTimer>> by ::_timers

    fun load() {
        _isLoading.value = true
        scope.launch {
            _timers.value = getLocalTimers.invoke()
            _isLoading.value = true
        }
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _timers: MutableStateFlow<List<LocalTimer>> = MutableStateFlow(emptyList())
}