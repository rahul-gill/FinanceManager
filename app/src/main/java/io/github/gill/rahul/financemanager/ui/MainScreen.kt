package io.github.gill.rahul.financemanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.gill.rahul.financemanager.R
import wow.app.core.ui.components.BoxedListItem
import wow.app.core.ui.components.BoxedListItemType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.random.Random


data class TxnItem(
    val title: String,
    val categoryColor: Color,
    val categoryName: String,
    val dateTime: LocalDateTime,
    val txnAmountFormatted: String,
    val id: String = UUID.randomUUID().toString(),
)

sealed class HeaderListItem {
    class Header(val value: LocalDate) : HeaderListItem()
    class Item(val value: TxnItem, val type: BoxedListItemType) : HeaderListItem()
}

val lists: List<HeaderListItem> = listOf(
    HeaderListItem.Header(LocalDate.now()),
    HeaderListItem.Item(
        TxnItem(
            "चाय",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now(),
            "- $ 100"
        ),
        type = BoxedListItemType.First
    ),
    HeaderListItem.Item(
        TxnItem(
            "घर आते टाइम",
            Color.Blue,
            "परिवहन",
            LocalDateTime.now().minusHours(10),
            "- $ 5"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Last
    ),
    HeaderListItem.Header(LocalDate.now().minusDays(1)),
    HeaderListItem.Item(
        TxnItem(
            "चाय",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1),
            "- $ 100"
        ),
        type = BoxedListItemType.First
    ),
    HeaderListItem.Item(
        TxnItem(
            "घर आते टाइम",
            Color.Blue,
            "परिवहन",
            LocalDateTime.now().minusDays(1).minusHours(10),
            "- $ 5"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Middle
    ),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(1).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Last
    ),
    HeaderListItem.Header(LocalDate.now().minusDays(2)),
    HeaderListItem.Item(
        TxnItem(
            "खाना",
            Color.Cyan,
            "खाने का सामान",
            LocalDateTime.now().minusDays(2).minusDays(15),
            "- $ 54.90"
        ),
        type = BoxedListItemType.Single
    ),
)

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier,
        transactionGroupType = DateRangeType.All,
        customRangeLengthDays = 5,
        goToCreateTxn = {},
        showDateRangeChooser = {}
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    transactionGroupType: DateRangeType,
    customRangeLengthDays: Long,
    goToCreateTxn: () -> Unit,
    showDateRangeChooser: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            if (transactionGroupType != DateRangeType.All) {
                IconButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {}//TODO
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "TODO")
                }
            }

            Text(
                text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()),//TODO
                style = MaterialTheme.typography.titleMedium
            )

            if (transactionGroupType != DateRangeType.All) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {}//TODO
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "TODO"
                    )
                }
            }
            Button(
                onClick = showDateRangeChooser,
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
        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(25.dp))
        ) {
            val state = rememberLazyListState()

            val monthFormat =
                DateTimeFormatter.ofPattern(stringResource(id = R.string.format_month))
            LazyColumn(state = state) {
                items(
                    items = lists,
                    contentType = { item ->
                        when (item) {
                            is HeaderListItem.Header -> "Header"
                            is HeaderListItem.Item -> "Item"
                        }
                    },
                    key = { item ->
                        when (item) {
                            is HeaderListItem.Header -> "${item.value}"
                            is HeaderListItem.Item -> item.value.id
                        }
                    }

                ) { item ->
                    when (item) {
                        is HeaderListItem.Header -> {
                            Text(
                                text = remember(item.value) {
                                    monthFormat.format(item.value)
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                        }

                        is HeaderListItem.Item -> {
                            val txn = item.value
                            val type = item.type

                            BoxedListItem(
                                modifier = Modifier
                                    .clickable {
                                        //do Some TODO
                                    },
                                type = type
                            ) {
                                TransactionItem(
                                    modifier = Modifier.padding(8.dp),
                                    title = txn.title,
                                    subtitle = remember(txn.categoryName, txn.dateTime) {
                                        "${txn.categoryName} / ${
                                            txn.dateTime.format(
                                                DateTimeFormatter.ofPattern("dd MMMM hh:mm")
                                            )
                                        }"
                                    },
                                    sideProminentText = txn.txnAmountFormatted,
                                    categoryColor = Random(System.currentTimeMillis()).nextBoolean()
                                        .run {
                                            if (this) Color.Cyan else Color.Magenta
                                        },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                )
                            }
                            if (type == BoxedListItemType.First || type == BoxedListItemType.Middle) {
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

            val header = remember {
                derivedStateOf {
                    when (val item = lists[state.firstVisibleItemIndex]) {
                        is HeaderListItem.Header -> null
                        is HeaderListItem.Item -> {
                            item.value.dateTime.format(monthFormat)
                        }
                    }
                }
            }
            if (header.value != null) {
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp)
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