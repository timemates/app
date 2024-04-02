package org.timemates.remote.value.list

import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a lazy-loading list flow that allows for paginated data retrieval.
 * This interface provides functionality for loading more items into the list when needed.
 *
 * Usually, implementation has caching mechanism.
 *
 * @param E The type of elements in the list.
 */
public interface LazyListContainer<E> {
    /**
     * A [StateFlow] representing whether more items can be loaded into the list.
     * This flow emits a boolean value indicating whether there are more items available to load.
     */
    public val hasMore: StateFlow<Boolean>

    /**
     * A [StateFlow] representing the current list of items.
     * This flow emits a list of elements representing the current state of the list.
     */
    public val list: StateFlow<List<E>>

    /**
     * Loads more items into the list.
     *
     * @param count The number of items to load.
     */
    public suspend fun load(
        count: Int,
        retry: suspend (Throwable) -> Boolean,
    )
}