package com.dima6120.core_impl.utils

import android.util.Log
import com.dima6120.core_api.model.Time
import com.dima6120.core_api.utils.DateFormatter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(): DateFormatter {

    private val mdyDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(MDY_DATE_PATTERN, Locale.US)
    }

    private val myDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(MY_DATE_PATTERN, Locale.US)
    }


    override fun formatDate(dateString: String): String? =
        try {
            when (dateString.count { it == '-' }) {
                0 -> dateString // 2025
                1 -> YearMonth.parse(dateString).format(myDateFormatter) // 2025-01
                else -> LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE).format(mdyDateFormatter) // 2025-01-25
            }
        } catch (e: Exception) {
            Log.w(TAG, e)
            null
        }


    override fun formatDateTime(dateTimeString: String): String? =
        try {
            LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME).format(mdyDateFormatter)
        } catch (e: Exception) {
            Log.w(TAG, e)
            null
        }

    override fun formatTime(seconds: Long): Time {
        check(seconds >= 0)

        val hours = TimeUnit.SECONDS.toHours(seconds)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)

        return when {
            hours != 0L -> {
                (minutes - TimeUnit.HOURS.toMinutes(hours))
                    .takeIf { it > 0 }
                    ?.let {
                        Time.HoursAndMinutes(hours, it)
                    }
                    ?: Time.Hours(hours)
            }
            minutes != 0L -> Time.Minutes(minutes)
            else -> Time.Seconds(seconds)
        }
    }

    companion object {

        private val TAG = DateFormatterImpl::class.java.simpleName

        private const val MDY_DATE_PATTERN = "MMM d, yyyy"
        private const val MY_DATE_PATTERN = "MMM yyyy"
    }
}