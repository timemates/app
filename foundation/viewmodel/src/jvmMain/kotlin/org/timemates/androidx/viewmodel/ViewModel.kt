package org.timemates.androidx.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

actual abstract class ViewModel {
    /**
     * The coroutine scope associated with this ViewModel.
     * Automatically cancels when ViewModel is not within user scope.
     */
    actual val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(
            Job() + Dispatchers.Default
        )
    }
}