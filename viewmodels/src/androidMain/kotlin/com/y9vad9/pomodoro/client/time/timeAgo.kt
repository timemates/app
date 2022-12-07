package com.y9vad9.pomodoro.client.time

import android.text.format.DateUtils
import com.y9vad9.pomodoro.client.types.value.DateTime
import java.text.ParseException
import java.util.*

actual fun DateTime.toTimeAgo(): String? {
    return try {
        val now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Kiev")).timeInMillis
        DateUtils.getRelativeTimeSpanString(long, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}