package io.github.gill.rahul.financemanager.db

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import wow.app.core.R
import wow.app.core.util.DateTimeUtils.dateRangeDayFormat
import wow.app.core.util.DateTimeUtils.dayFormat
import wow.app.core.util.DateTimeUtils.monthFormat
import wow.app.core.util.DateTimeUtils.yearFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

sealed class DateRangeType {
    data object Daily : DateRangeType()
    data object Weekly : DateRangeType()
    data object Monthly : DateRangeType()
    data object Yearly : DateRangeType()
    data object All : DateRangeType()
    data class Custom(val daysInRange: Long) : DateRangeType()

    val stringRepresentation
        @Composable get() = stringResource(id = nameStringRes)

    @get:StringRes
    val nameStringRes: Int
        get() = when (this) {
            All -> R.string.all_time
            is Custom -> R.string.custom
            Daily -> R.string.daily
            Monthly -> R.string.monthly
            Weekly -> R.string.weekly
            Yearly -> R.string.yearly
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


    fun formatDateRange(startDate: LocalDate = LocalDate.now()): String {
        return when (this) {
            All -> ""
            is Custom -> formatDateRange(startDate, startDate.plusDays(daysInRange))
            Daily -> dayFormat.format(startDate)
            Monthly -> monthFormat.format(startDate)
            Weekly -> formatWeekRange(weekDate = startDate)
            Yearly -> yearFormat.format(startDate)
        }
    }

    private fun formatDateRange(start: LocalDate, end: LocalDate): String {
        return dateRangeDayFormat.format(start) + " - " + dateRangeDayFormat.format(end)
    }

    private fun formatWeekRange(weekDate: LocalDate): String {
        return formatDateRange(
            weekDate.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
            ),
            weekDate.with(
                TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)
            )
        )
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

    fun LocalDate.isInDateRange(
        dateRangeType: DateRangeType,
        currentRangeStart: LocalDate
    ): Boolean {
        return when (dateRangeType) {
            All -> true
            is Custom -> {
                val rangeEnd = currentRangeStart.plusDays(dateRangeType.daysInRange)
                isBefore(rangeEnd) && (isAfter(currentRangeStart) || this == currentRangeStart)
            }
            Daily -> this == currentRangeStart
            Monthly -> {
                month == currentRangeStart.month &&
                        year == currentRangeStart.year
            }
            Weekly -> {
                with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    .equals(currentRangeStart.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
            }
            Yearly -> {
                year == currentRangeStart.year
            }
        }
    }
}
