package com.dima6120.core_api.utils

interface DateFormatter {

    fun formatDate(dateString: String): String?

    fun formatDateTime(dateTimeString: String): String?
}