package com.dima6120.core_impl.utils

import android.util.Log
import com.dima6120.core_api.utils.DateFormatter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(): DateFormatter {

    private val dateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.US)
    }

    override fun formatDate(dateString: String): String? =
        try {
            when (dateString.count { it == '-' }) {
                0 -> dateString // 2025
                1 -> YearMonth.parse(dateString).format(dateFormatter) // 2025-01
                else -> LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE).format(dateFormatter) // 2025-01-25
            }
        } catch (e: Exception) {
            Log.w(TAG, e)
            null
        }


    override fun formatDateTime(dateTimeString: String): String? =
        try {
            LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME).format(dateFormatter)
        } catch (e: Exception) {
            Log.w(TAG, e)
            null
        }

    companion object {

        private val TAG = DateFormatterImpl::class.java.simpleName
        private const val DATE_PATTERN = "MMM d, yyyy"
    }
}