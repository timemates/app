package io.timemates.remote.value

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow

internal class UpdatableSmartValue<T>(
    private val local: suspend () -> T?,
    private val remote: suspend () -> T?,
    private val localUpdates: ReceiveChannel<T>? = null,
    private val remoteUpdates: ReceiveChannel<T>? = null,
) : SmartValue<T> {
    override val localValue: Flow<T?> = flow {
        emit(local())

        localUpdates?.receiveAsFlow()?.collect {
            emit(it)
        }
    }
    override val remoteValue: Flow<T?> = flow {
        emit(remote())

        remoteUpdates?.receiveAsFlow()?.collect {
            emit(it)
        }
    }
}