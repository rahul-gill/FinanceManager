package io.github.gill.rahul.financemanager.ui.components

import io.github.gill.rahul.financemanager.R
import io.github.gill.rahul.financemanager.ui.Strs
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

sealed class DateRangeType {
    data object Daily : DateRangeType()
    data object Weekly : DateRangeType()
    data object Monthly : DateRangeType()
    data object Yearly : DateRangeType()
    data object All : DateRangeType()
    data class Custom(val daysInRange: Long) : DateRangeType()

    fun formatWithStartDate(startDate: LocalDate = LocalDate.now()): String {
        return when (this) {
            All -> Strs.get(R.string.all_time)
            is Custom -> formatDateRange(
                rangeStart = startDate,
                rangeEnd = startDate.plusDays(daysInRange)
            )

            Daily -> Strs.get(R.string.format_daily)
            Monthly -> Strs.get(R.string.format_month)
            Weekly -> formatDateRange(
                rangeStart = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                rangeEnd = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            )

            Yearly -> DateTimeFormatter.ofPattern(Strs.get(R.string.format_year)).format(startDate)
        }
    }

    private fun formatDateRange(rangeStart: LocalDate, rangeEnd: LocalDate): String {
        val dateRangeDayFormat = Strs.get(R.string.format_date_range_day)
        val weekStartStr = DateTimeFormatter.ofPattern(dateRangeDayFormat).format(rangeStart)
        val weekEndStr = DateTimeFormatter.ofPattern(dateRangeDayFormat).format(rangeEnd)
        return Strs.get(R.string.format_date_range, weekStartStr, weekEndStr)
    }

    fun calculatePreviousStartDate(startDate: LocalDate = LocalDate.now()): LocalDate {
        return when (this) {
            All -> error("All date range doesn't support calculatePreviousStartDate")
            is Custom -> startDate.minusDays(daysInRange)
            Daily -> startDate.minusDays(1)
            Monthly -> startDate.minusMonths(1)
            Weekly -> startDate.minusWeeks(1)
            Yearly -> startDate.plusYears(1)
        }
    }

    fun calculateNextStartDate(startDate: LocalDate = LocalDate.now()): LocalDate {
        return when (this) {
            All -> error("All date range doesn't support calculateNextStartDate")
            is Custom -> startDate.plusDays(daysInRange)
            Daily -> startDate.plusDays(1)
            Monthly -> startDate.plusMonths(1)
            Weekly -> startDate.plusWeeks(1)
            Yearly -> startDate.plusYears(1)
        }
    }

    fun typeName(): String {
        return Strs.get(
            when (this) {
                All -> R.string.all_time
                is Custom -> R.string.custom
                Daily -> R.string.daily
                Monthly -> R.string.monthly
                Weekly -> R.string.weekly
                Yearly -> R.string.yearly
            }
        )
    }
}
