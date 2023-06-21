package io.timemates.androidx.viewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * Abstract class representing a ViewModel.
 */
expect abstract class ViewModel() {
    /**
     * The coroutine scope associated with this ViewModel.
     * Automatically cancels when ViewModel is not within user scope.
     */
    val coroutineScope: CoroutineScope
}
