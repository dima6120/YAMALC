package com.dima6120.core_api.utils

import com.dima6120.core_api.model.Time

interface DateFormatter {

    fun formatDate(dateString: String): String?

    fun formatDateTime(dateTimeString: String): String?

    fun formatTime(seconds: Long): Time
}