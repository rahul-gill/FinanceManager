package io.github.gill.rahul.financemanager.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.gill.rahul.financemanager.R
import io.github.gill.rahul.financemanager.StringResources
import io.github.gill.rahul.financemanager.ui.theme.FinanceManagerTheme
import java.time.LocalDate

sealed class DateRangeType {
    data object Daily : DateRangeType()
    data object Weekly : DateRangeType()
    data object Monthly : DateRangeType()
    data object Yearly : DateRangeType()
    data object All : DateRangeType()
    data class Custom(val daysInRange: Long) : DateRangeType()

    fun formatWithStartDate(startDate: LocalDate = LocalDate.now()): String {
        return when (this) {
            //TODO
            All -> StringResources.get(R.string.all_time)
            is Custom -> StringResources.get(R.string.all_time)
            Daily -> StringResources.get(R.string.all_time)
            Monthly -> StringResources.get(R.string.all_time)
            Weekly -> StringResources.get(R.string.all_time)
            Yearly -> StringResources.get(R.string.all_time)
        }
    }

    fun calculatePreviousStartDate(startDate: LocalDate = LocalDate.now()): LocalDate {
        return when (this) {
            All -> throw IllegalStateException("All date range doesn't support calculatePreviousStartDate")
            is Custom -> startDate.minusDays(daysInRange)
            Daily -> startDate.minusDays(1)
            Monthly -> startDate.minusMonths(1)
            Weekly -> startDate.minusWeeks(1)
            Yearly -> startDate.plusYears(1)
        }
    }


    fun calculateNextStartDate(startDate: LocalDate = LocalDate.now()): LocalDate {
        return when (this) {
            All -> throw IllegalStateException("All date range doesn't support calculateNextStartDate")
            is Custom -> startDate.plusDays(daysInRange)
            Daily -> startDate.plusDays(1)
            Monthly -> startDate.plusMonths(1)
            Weekly -> startDate.plusWeeks(1)
            Yearly -> startDate.plusYears(1)
        }
    }

    fun typeName(): String {
        return StringResources.get(
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

@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    dateRangeType: DateRangeType,
    dateRangeStart: LocalDate,
    onPreviousRange: () -> Unit,
    onNextRange: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (dateRangeType != DateRangeType.All) {
            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = onPreviousRange
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "TODO")
            }
        }

        Text(
            text = dateRangeType.formatWithStartDate(dateRangeStart),
            style = MaterialTheme.typography.titleMedium
        )

        if (dateRangeType != DateRangeType.All) {
            IconButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onNextRange
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "TODO")
            }
        }
    }
}

@Composable
fun DateRangeTypeButton(
    modifier: Modifier = Modifier,
    dateRangeType: DateRangeType,
    onClick: () -> Unit
){
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = dateRangeType.typeName()
        )
        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
    }
}


@Preview(showBackground = true)
@Composable
private fun Previews(){
    var dateRangeType: DateRangeType by remember {
        mutableStateOf(DateRangeType.Daily)
    }
    var dateRangeStartDate by remember {
        mutableStateOf(LocalDate.now())
    }
    FinanceManagerTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateRangeSelector(
                dateRangeType = dateRangeType,
                dateRangeStart = dateRangeStartDate,
                onPreviousRange = { dateRangeStartDate = dateRangeType.calculatePreviousStartDate(dateRangeStartDate) },
                onNextRange = { dateRangeStartDate = dateRangeType.calculateNextStartDate(dateRangeStartDate) },
            )
            DateRangeTypeButton(dateRangeType = dateRangeType, onClick = {
                /*TODO*/
                dateRangeType = listOf(
                    DateRangeType.Daily,
                    DateRangeType.All,
                    DateRangeType.Weekly,
                    DateRangeType.Monthly,
                    DateRangeType.Yearly,
                    DateRangeType.Custom(10)
                ).random()
            })
        }
    }
}