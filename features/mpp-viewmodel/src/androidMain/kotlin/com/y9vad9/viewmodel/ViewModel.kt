package com.y9vad9.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual abstract class ViewModel : ViewModel() {
    actual val scope: CoroutineScope = viewModelScope
}