package io.github.gill.rahul.financemanager.ui.screen.dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.pop
import io.github.gill.rahul.financemanager.db.GetAllTransactions
import io.github.gill.rahul.financemanager.db.Transactions
import io.github.gill.rahul.financemanager.db.TransactionsOperations
import io.github.gill.rahul.financemanager.models.TransactionUiModel
import io.github.gill.rahul.financemanager.ui.DateRangeType
import kotlinx.coroutines.delay
import wow.app.core.R
import wow.app.core.ui.components.AlertDialog
import wow.app.core.ui.components.BaseDialog
import wow.app.core.util.DateTimeUtils
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.random.Random


sealed class HeaderListItem {
    class Header(val value: LocalDate) : HeaderListItem()
    class Item(val value: TransactionUiModel) : HeaderListItem()
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    goToCreateTxn: () -> Unit
) {
    var transactionGroupType: DateRangeType by remember {
        mutableStateOf(DateRangeType.Daily)
    }
    var currentRangeStart by remember {
        mutableStateOf(LocalDate.now())
    }
    var customRangeLengthDays by remember {
        mutableLongStateOf(10L)
    }
    var showDateRangeChooser by remember {
        mutableStateOf(false)
    }
    var showCustomRangeLengthChooser by remember {
        mutableStateOf(false)
    }
    Column(modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.Center
        ) {

            Row {
                if (transactionGroupType != DateRangeType.All) {
                    IconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = { currentRangeStart = transactionGroupType.calculatePreviousStartDate(currentRangeStart) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "TODO"
                        )
                    }
                }

                Text(
                    text = transactionGroupType.formatDateRange(currentRangeStart),
                    style = MaterialTheme.typography.titleMedium
                )

                if (transactionGroupType != DateRangeType.All) {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = { currentRangeStart = transactionGroupType.calculatePreviousStartDate(currentRangeStart) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "TODO"
                        )
                    }
                }
            }
            Button(
                onClick = { showDateRangeChooser = true },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text(
                    text = stringResource(transactionGroupType.nameStringRes)
                )
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
        ElevatedCard(
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Column(Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.income),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text("$ 10")
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.expense),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text("$ 10,000")
                }
                Divider(
                    Modifier
                        .height(1.dp)
                        .padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.balance),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text("$ 100,000")
                }
            }
        }
        val txnList =
            TransactionsOperations.instance.getAllTransactions().collectAsState(initial = listOf())
        val lists = remember(txnList.value) {
            val finalList = mutableListOf<HeaderListItem>()
            var prevDateTime: LocalDateTime? = null
            txnList.value
                .sortedWith { first, second ->
                    when {
                        first.dateTime.year > second.dateTime.year -> 1
                        first.dateTime.year < second.dateTime.year -> -1
                        first.dateTime.month > second.dateTime.month -> 1
                        first.dateTime.month < second.dateTime.month -> -1
                        else -> 0
                    }
                }.forEach { item ->
                    if (prevDateTime == null || prevDateTime!!.year != item.dateTime.year || prevDateTime!!.month != item.dateTime.month) {
                        finalList.add(HeaderListItem.Header(item.dateTime.toLocalDate()))
                    }
                    finalList.add(HeaderListItem.Item(item))
                    prevDateTime = item.dateTime
                }
            finalList
        }
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()) {
            val state = rememberLazyListState()
            val shouldHeaderBeVisible = remember {
                mutableStateOf(false)
            }
            LaunchedEffect(state.isScrollInProgress) {
                if (state.isScrollInProgress) {
                    shouldHeaderBeVisible.value = true
                } else {
                    delay(2000)
                    shouldHeaderBeVisible.value = false
                }
            }
            LazyColumn(state = state, modifier = Modifier.testTag("dashboard:transaction_list")) {
                itemsIndexed(
                    items = lists,
                    contentType = { _, item ->
                        when (item) {
                            is HeaderListItem.Header -> "Header"
                            is HeaderListItem.Item -> "Item"
                        }
                    },
                    key = { _, item ->
                        when (item) {
                            is HeaderListItem.Header -> "${item.value}"
                            is HeaderListItem.Item -> item.value.id
                        }
                    }

                ) { index, item ->
                    when (item) {
                        is HeaderListItem.Header -> {
                            Text(
                                text = remember(item.value) {
                                    DateTimeUtils.monthFormat.format(item.value)
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                        }

                        is HeaderListItem.Item -> {
                            val txn = item.value

                            TransactionItem(
                                modifier = Modifier
                                    .clickable {
                                        //do Some TODO
                                    }
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                title = txn.title ?: "",
                                subtitle = remember(txn.categoryName, txn.dateTime) {
                                    "${txn.categoryName} / ${
                                        txn.dateTime.format(DateTimeUtils.simpleDateAndTimeFormat)
                                    }"
                                },
                                sideProminentText = txn.amount.formattedValue,
                                categoryColor = item.value.color,
                                icon = {
                                    Icon(
                                        painter = painterResource(id = item.value.iconRes),
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            )

                            if (index < lists.lastIndex) {
                                Spacer(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                )
                            }

                        }
                    }
                }
                item {
                    Spacer(Modifier.height(100.dp))
                }
            }

            val header = remember(lists) {
                derivedStateOf {
                    when (val item = lists.getOrNull(state.firstVisibleItemIndex)) {
                        is HeaderListItem.Header -> {
                            (lists[state.firstVisibleItemIndex + 1] as HeaderListItem.Item).value.dateTime.format(
                                DateTimeUtils.monthFormat
                            )
                        }

                        is HeaderListItem.Item -> {
                            item.value.dateTime.format(DateTimeUtils.monthFormat)
                        }

                        null -> "Err"
                    }
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = shouldHeaderBeVisible.value,
                enter = fadeIn() + slideInVertically(),
                exit = slideOutVertically() + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50))
                        .animateContentSize(),
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = header.value!!,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                    )
                }
            }
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                onClick = goToCreateTxn,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            ) {
                Text(text = stringResource(R.string.create_new_transaction))
            }
        }
    }

    if (showDateRangeChooser) {
        BaseDialog(onDismissRequest = { showDateRangeChooser = false }) {
            val choiceList = remember {
                listOf(
                    DateRangeType.Daily,
                    DateRangeType.Weekly,
                    DateRangeType.Monthly,
                    DateRangeType.Yearly,
                    DateRangeType.Custom(customRangeLengthDays)
                )
            }
            Card {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.group_transactions_by),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Column(Modifier.selectableGroup()) {
                    choiceList.forEach { choice ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (choice == transactionGroupType),
                                    onClick = {
                                        transactionGroupType = choice
                                        showDateRangeChooser = false
                                        if (choice is DateRangeType.Custom) {
                                            showCustomRangeLengthChooser = true
                                        }
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (transactionGroupType == choice),
                                onClick = null
                            )
                            Text(
                                text = stringResource(choice.nameStringRes),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .weight(1f)
                            )
                            if (choice is DateRangeType.Custom) {
                                IconButton(
                                    onClick = { showCustomRangeLengthChooser = true },
                                    enabled = transactionGroupType is DateRangeType.Custom
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EditCalendar,
                                        contentDescription = "TODO"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showCustomRangeLengthChooser) {
        BaseDialog(
            onDismissRequest = { showCustomRangeLengthChooser = false }
        ) {
            val dateRangePickerState = rememberDateRangePickerState()
            DateRangePicker(
                title = { Text(text = "Select custom date range") },
                state = dateRangePickerState,
                modifier = Modifier.weight(1f)
            )
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { showCustomRangeLengthChooser = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    enabled = dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null,
                    onClick = {
                        val end = Instant.ofEpochMilli(dateRangePickerState.selectedEndDateMillis!!)
                        val start =
                            Instant.ofEpochMilli(dateRangePickerState.selectedStartDateMillis!!)
                        val diffDays = Duration.between(end, start).toDays()
                        println("diffDays:$diffDays")
                        customRangeLengthDays = diffDays
                        currentRangeStart = LocalDate.ofInstant(start, ZoneId.systemDefault())
                        showCustomRangeLengthChooser = false
                    }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}


@Composable
fun TransactionItem(
    modifier: Modifier,
    title: String,
    subtitle: String,
    sideProminentText: String,
    categoryColor: Color,
    icon: @Composable () -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        val shape = remember { RoundedCornerShape(15) }
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(30.dp)
                .background(color = categoryColor, shape = shape)
                .clip(shape),
            propagateMinConstraints = true,
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.padding(4.dp)) {
                icon()
            }
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = subtitle, style = MaterialTheme.typography.labelMedium)
        }
        Text(text = sideProminentText, style = MaterialTheme.typography.headlineSmall)
    }
}