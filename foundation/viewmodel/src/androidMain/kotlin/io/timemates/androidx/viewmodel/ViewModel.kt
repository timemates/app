package io.timemates.androidx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual abstract class ViewModel : ViewModel() {
    /**
     * The coroutine scope associated with this ViewModel.
     *
     * The `viewModelScope` is a [CoroutineScope] provided by the Android Jetpack's ViewModel library.
     * It is used to launch coroutines that are scoped to the lifecycle of the ViewModel.
     * Any coroutines launched in this scope will automatically be canceled when the ViewModel is cleared or destroyed.
     *
     * @see [ViewModel]
     * @see [CoroutineScope]
     */
    actual val coroutineScope: CoroutineScope by ::viewModelScope
}