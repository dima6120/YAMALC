package com.dima6120.core_impl.utils

import android.util.Log
import com.dima6120.core_impl.utils.DateFormatterImpl
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

class DateFormatterImplUnitTests {

    private val dateFormatterImpl = DateFormatterImpl()

    @Before
    fun before() {
        mockkStatic(Log::class)

        every { Log.w(any(), any<Throwable>()) } returns 0
    }

    @Test
    fun yearDateString() {
        // given
        val dateString = "2025"
        // when
        val formattedDateString = dateFormatterImpl.formatDate(dateString)
        // then
        assert(formattedDateString == "2025")
    }

    @Test
    fun yearMonthDateString() {
        // given
        val dateString = "2025-01"
        // when
        val formattedDateString = dateFormatterImpl.formatDate(dateString)
        // then
        assert(formattedDateString == "Jan 2025")
    }

    @Test
    fun yearMonthDayDateString() {
        // given
        val dateString = "2025-01-10"
        // when
        val formattedDateString = dateFormatterImpl.formatDate(dateString)
        // then
        assert(formattedDateString == "Jan 10, 2025")
    }

    @Test
    fun dateTimeString() {
        // given
        val dateTimeString = "2025-01-02T06:03:11+00:00"
        // when
        val formattedDateString = dateFormatterImpl.formatDateTime(dateTimeString)
        // then
        assert(formattedDateString == "Jan 2, 2025")
    }
}