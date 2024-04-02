package org.timemates.remote.value.list

public interface RemoteMediator {
    public suspend fun initialize(): InitialAction

    public suspend fun load()

    public enum class InitialAction {
        SKIP_INITIAL_REFRESH, LAUNCH_INITIAL_REFRESH,
    }
}