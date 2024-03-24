package org.timemates.remote.value

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

/**
 * A generic interface representing a remote value with local storage capabilities.
 *
 * This interface provides access to the current value, local value, and remote value.
 * It also supports setting a listener to be notified when the value is received.
 *
 * @param T The type of the value.
 */
public interface SmartValue<T> {
    /**
     * A [Flow] representing the local value. This flow can emit multiple values if any change
     * has occurred in the local storage (if watching for updates is supported).
     *
     * **Note**: Some [SmartValue]s can support remote only value meaning there's no
     * value will be present.
     *
     * @see kotlinx.coroutines.flow.Flow
     */
    public val localValue: Flow<T?>

    /**
     * A [Flow] representing the remote value. This flow can have multiple outputs depending on the
     * implementation, as it may receive updates from external sources, such as the server.
     *
     * **Note**: [remoteValue] shouldn't be considered as 'last actual source' as for specific cases
     * [localValue] can receive updates that made on client that appears much faster than on server
     * or actual 'update watching' system could be missing.
     *
     * @see kotlinx.coroutines.flow.Flow
     */
    public val remoteValue: Flow<T?>

    public companion object {
        /**
         * A factory method for creating instances of [SmartValue].
         *
         * This method is used to construct a [SmartValue] instance with specified local and remote update
         * logic, along with optional channels for receiving updates from local and remote sources.
         *
         * @param T The type of the value.
         * @param local A suspending lambda representing the logic to retrieve the initial local value.
         * @param remote A suspending lambda representing the logic to retrieve the initial remote value.
         * @param localUpdates An optional [ReceiveChannel] to receive updates from local sources.
         * @param remoteUpdates An optional [ReceiveChannel] to receive updates from remote sources.
         * @return A [SmartValue] instance configured with the provided parameters.
         *
         * @see SmartValue
         * @see kotlinx.coroutines.channels.ReceiveChannel
         */
        public fun <T> of(
            local: suspend () -> T?,
            remote: suspend () -> T?,
            localUpdates: ReceiveChannel<T>? = null,
            remoteUpdates: ReceiveChannel<T>? = null,
        ): SmartValue<T> {
            return UpdatableSmartValue(
                local, remote, localUpdates, remoteUpdates
            )
        }
    }
}

public fun <T, R> SmartValue<T>.map(transform: suspend (T?) -> R?): SmartValue<R> {
    return MappingSmartValue(this, transform)
}