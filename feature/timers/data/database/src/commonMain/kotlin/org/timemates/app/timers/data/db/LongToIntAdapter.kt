package org.timemates.app.timers.data.db

import app.cash.sqldelight.ColumnAdapter

object LongToIntAdapter : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int {
        return databaseValue.toInt()
    }
    override fun encode(value: Int): Long {
        return value.toLong()
    }
}