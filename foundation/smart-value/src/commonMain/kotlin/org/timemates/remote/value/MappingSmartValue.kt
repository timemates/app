package org.timemates.remote.value

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class MappingSmartValue<T, R>(
    source: SmartValue<T>,
    mapper: suspend (T?) -> R?
) : SmartValue<R> {
    override val localValue: Flow<R?> = source.localValue.map(mapper)
    override val remoteValue: Flow<R?> = source.remoteValue.map(mapper)
}