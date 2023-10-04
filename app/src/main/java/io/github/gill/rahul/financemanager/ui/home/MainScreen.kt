package io.github.gill.rahul.financemanager.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.SubdirectoryArrowLeft
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import io.github.gill.rahul.financemanager.ui.components.DateRangeType
import io.github.gill.rahul.financemanager.ui.components.SingleChoiceDialog
import io.github.gill.rahul.financemanager.ui.components.formatWithStartDate
import io.github.gill.rahul.financemanager.ui.components.name
import io.github.gill.rahul.financemanager.ui.theme.FinanceManagerTheme
import io.github.gill.rahul.financemanager.ui.theme.MoneyManagerPreviews
import io.github.gill.rahul.financemanager.ui.theme.PreviewWrapper
import io.github.gill.rahul.financemanager.ui.theme.getContentColorForBackground
import io.github.gill.rahul.financemanager.util.millisToLocalDate
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit

// TODO 1: fix dialogs and income expense icons and their onClick events
// TODO 2: implement create transaction screen
// TODO 3: setup data things

@MoneyManagerPreviews
@Composable
private fun MainScreenPreview() {
    PreviewWrapper {
        HomeScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    var selectedDateRangeType: DateRangeType by remember {
        mutableStateOf(DateRangeType.Daily)
    }
    var dateRangeStartDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var showDateRangeTypeDialog by remember {
        mutableStateOf(false)
    }
    var showCustomDateRangeSelectorDialog by remember {
        mutableStateOf(false)
    }
    var customRangeLengthDays by remember {
        mutableLongStateOf(10L)
    }
    Box {
        val listState = rememberLazyListState()
        val fabVisible by remember {
            derivedStateOf { !listState.isScrollInProgress }
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DateRangeSelector(
                    dateRangeType = selectedDateRangeType,
                    dateRangeStart = dateRangeStartDate,
                    onPreviousRange = {
                        dateRangeStartDate =
                            selectedDateRangeType.calculatePreviousStartDate(dateRangeStartDate)
                    },
                    onNextRange = {
                        dateRangeStartDate =
                            selectedDateRangeType.calculateNextStartDate(dateRangeStartDate)
                    },
                )
                FilledTonalButton(onClick = {
                    showDateRangeTypeDialog = true
                }) {
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = selectedDateRangeType.name(LocalContext.current)
                    )
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                IncomeExpenseCard(
                    modifier = Modifier.weight(1f),
                    title = "Income",
                    amount = "0",
                    icon = Icons.Default.SubdirectoryArrowLeft,
                    iconColor = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                IncomeExpenseCard(
                    modifier = Modifier.weight(1f),
                    title = "Expense",
                    amount = "9774",
                    icon = Icons.Default.SubdirectoryArrowRight,
                    iconColor = MaterialTheme.colorScheme.secondary
                )
            }
            LazyColumn(state = listState) {
                // TODO
                stickyHeader {
                    TransactionsListHeader(
                        headerTitle = "Sun, Oct 1",
                        overallBalanceChange = "Overall: -30"
                    )
                }
                for (i in 1..15) {
                    item {
                        TransactionsListItem(Modifier.padding(8.dp))
                    }
                }
                stickyHeader {
                    TransactionsListHeader(
                        headerTitle = "Sun, Oct 2",
                        overallBalanceChange = "Overall: -30"
                    )
                }
                for (i in 1..15) {
                    item {
                        TransactionsListItem(Modifier.padding(8.dp))
                    }
                }
                stickyHeader {
                    TransactionsListHeader(
                        headerTitle = "Sun, Oct 3",
                        overallBalanceChange = "Overall: -30"
                    )
                }
                for (i in 1..15) {
                    item {
                        TransactionsListItem(Modifier.padding(8.dp))
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = fabVisible,
            enter = slideIn { IntOffset(it.width, 0) },
            exit = slideOut { IntOffset(it.width, 0) }
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(8.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "TODO")
            }
        }
    }
    if (showDateRangeTypeDialog) {
        val context = LocalContext.current
        SingleChoiceDialog(
            onDismiss = { showDateRangeTypeDialog = false },
            title = "Group transactions by",
            choiceList = remember {
                listOf(
                    DateRangeType.Daily,
                    DateRangeType.Weekly,
                    DateRangeType.Monthly,
                    DateRangeType.Yearly,
                    DateRangeType.Custom(customRangeLengthDays)
                )
            },
            selectedChoice = selectedDateRangeType,
            setChoice = { type ->
                if (type is DateRangeType.Custom) {
                    showDateRangeTypeDialog = false
                    showCustomDateRangeSelectorDialog = true
                } else {
                    selectedDateRangeType = type
                    showDateRangeTypeDialog = false
                }

            },
            choiceName = { it.name(context) }
        )
    }
    if (showCustomDateRangeSelectorDialog) {
        val dateRangeState = rememberDateRangePickerState()
        DatePickerDialog(
            onDismissRequest = { showCustomDateRangeSelectorDialog = false },
            confirmButton = {
                TextButton(
                    enabled = dateRangeState.selectedEndDateMillis != null
                            && dateRangeState.selectedStartDateMillis != null,
                    onClick = {
                        customRangeLengthDays = dateRangeState.run {
                            (selectedEndDateMillis!! - selectedStartDateMillis!!) / 86400000L
                        }
                        dateRangeStartDate =
                            millisToLocalDate(dateRangeState.selectedStartDateMillis!!)
                        showCustomDateRangeSelectorDialog = false
                    }
                ) {
                    Text(text = "Apply")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomDateRangeSelectorDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DateRangePicker(state = dateRangeState)
        }
    }
}

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
            text = dateRangeType.formatWithStartDate(LocalContext.current, dateRangeStart),
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
fun IncomeExpenseCard(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(text = title)
                Text(text = amount, style = MaterialTheme.typography.titleMedium)
            }
            Surface(
                modifier = Modifier,
                color = iconColor,
                shape = CircleShape
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Composable
fun TransactionsListHeader(
    modifier: Modifier = Modifier,
    headerTitle: String,
    overallBalanceChange: String
) {
    Surface(
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = headerTitle, style = MaterialTheme.typography.labelMedium)
            Text(text = overallBalanceChange, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
@MoneyManagerPreviews
private fun TransactionsListItemPreview() {
    FinanceManagerTheme {
        TransactionsListItem()
    }
}

@Composable
fun TransactionsListItem(
    modifier: Modifier = Modifier,
    title: String = "Tea",
    subtitle: String = "Food and drinks",
    icon: ImageVector = Icons.Default.Fastfood,
    color: Color = Color.Yellow
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.minimumInteractiveComponentSize(),
                shape = RoundedCornerShape(25),
                color = color
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp),
                    tint = getContentColorForBackground(color)
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(text = "-15", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeTypeSelectionDialog(
    onDismiss: () -> Unit,
    customRangeLengthDays: Long,
    dateRangeType: DateRangeType,
    setDateRangeType: (DateRangeType) -> Unit,
    openCustomDateRangeSelectorDialog: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss) {
        val radioOptions = listOf(
            DateRangeType.Daily,
            DateRangeType.Weekly,
            DateRangeType.Monthly,
            DateRangeType.Yearly,
            DateRangeType.Custom(customRangeLengthDays)
        )
        Card {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Group transactions by",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { type ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (type == dateRangeType),
                                onClick = {
                                    if (type is DateRangeType.Custom) {
                                        openCustomDateRangeSelectorDialog()
                                    } else {
                                        setDateRangeType(type)
                                    }
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (dateRangeType == type),
                            onClick = null
                        )
                        Text(
                            text = type.name(LocalContext.current),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
