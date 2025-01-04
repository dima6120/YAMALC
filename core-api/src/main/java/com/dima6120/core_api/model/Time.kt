package com.dima6120.core_api.model

sealed class Time {

    data class Seconds(val seconds: Long): Time()

    data class Minutes(val minutes: Long): Time()

    data class Hours(val hours: Long): Time()

    data class HoursAndMinutes(val hours: Long, val minutes: Long): Time()
}