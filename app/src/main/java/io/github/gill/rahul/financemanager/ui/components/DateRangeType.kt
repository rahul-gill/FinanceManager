package io.github.gill.rahul.financemanager.ui.components

import android.content.Context
import io.github.gill.rahul.financemanager.R
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
}


fun DateRangeType.formatWithStartDate(
    context: Context,
    startDate: LocalDate = LocalDate.now()
): String {
    val formatDateRange = { rangeStart: LocalDate, rangeEnd: LocalDate ->
        val dateRangeDayFormat = context.getString(R.string.format_date_range_day)
        val weekStartStr = DateTimeFormatter.ofPattern(dateRangeDayFormat).format(rangeStart)
        val weekEndStr = DateTimeFormatter.ofPattern(dateRangeDayFormat).format(rangeEnd)
        context.getString(R.string.format_date_range, weekStartStr, weekEndStr)
    }
    return when (this) {
        DateRangeType.All -> context.getString(R.string.all_time)
        is DateRangeType.Custom -> formatDateRange(
            startDate,
            startDate.plusDays(daysInRange)
        )

        DateRangeType.Daily -> DateTimeFormatter
            .ofPattern(context.getString(R.string.format_daily))
            .format(startDate)

        DateRangeType.Monthly -> DateTimeFormatter
            .ofPattern(context.getString(R.string.format_month))
            .format(startDate)

        DateRangeType.Weekly -> formatDateRange(
            startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
            startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        )

        DateRangeType.Yearly ->
            DateTimeFormatter.ofPattern(context.getString(R.string.format_year)).format(startDate)
    }
}

fun DateRangeType.name(context: Context): String {
    return context.getString(
        when (this) {
            DateRangeType.All -> R.string.all_time
            is DateRangeType.Custom -> R.string.custom
            DateRangeType.Daily -> R.string.daily
            DateRangeType.Monthly -> R.string.monthly
            DateRangeType.Weekly -> R.string.weekly
            DateRangeType.Yearly -> R.string.yearly
        }
    )
}

