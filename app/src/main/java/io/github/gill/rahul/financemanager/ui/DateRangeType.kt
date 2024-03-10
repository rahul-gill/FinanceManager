package io.github.gill.rahul.financemanager.ui

import androidx.annotation.StringRes
import wow.app.core.R
import java.time.LocalDate

sealed class DateRangeType {
    data object Daily : DateRangeType()
    data object Weekly : DateRangeType()
    data object Monthly : DateRangeType()
    data object Yearly : DateRangeType()
    data object All : DateRangeType()
    data class Custom(val daysInRange: Long) : DateRangeType()

    @get:StringRes
    val nameStringRes: Int
        get(){
            return when (this) {
                All -> R.string.all_time
                is Custom -> R.string.custom
                Daily -> R.string.daily
                Monthly -> R.string.monthly
                Weekly -> R.string.weekly
                Yearly -> R.string.yearly
            }
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
}
