package org.timemates.app.timers.data.datasource

import app.cash.sqldelight.db.SqlDriver
import io.timemates.data.database.DbInvite
import io.timemates.data.database.DbInviteQueries
import org.timemates.app.timers.data.db.LongToIntAdapter

class DatabaseTimerInvitesSource(
    driver: SqlDriver,
) {
    private val queries: DbInviteQueries = DbInviteQueries(driver, DbInvite.Adapter(LongToIntAdapter))
}