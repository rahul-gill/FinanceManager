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
import io.github.gill.rahul.financemanager.ui.theme.FinanceManagerTheme
import java.time.LocalDate

@Composable
fun DateRangeSelector(
    dateRangeType: DateRangeType,
    dateRangeStart: LocalDate,
    onPreviousRange: () -> Unit,
    onNextRange: () -> Unit,
    modifier: Modifier = Modifier,
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
    dateRangeType: DateRangeType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
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
private fun Previews() {
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
                onPreviousRange = {
                    dateRangeStartDate =
                        dateRangeType.calculatePreviousStartDate(dateRangeStartDate)
                },
                onNextRange = {
                    dateRangeStartDate = dateRangeType.calculateNextStartDate(dateRangeStartDate)
                },
            )
            DateRangeTypeButton(dateRangeType = dateRangeType, onClick = {
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
