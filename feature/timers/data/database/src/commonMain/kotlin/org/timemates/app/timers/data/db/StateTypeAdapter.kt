package org.timemates.app.timers.data.db

import app.cash.sqldelight.ColumnAdapter

object StateTypeAdapter : ColumnAdapter<StateType, String> {
    override fun decode(databaseValue: String): StateType {
        return StateType.valueOf(databaseValue)
    }
    override fun encode(value: StateType): String {
        return value.name
    }
}