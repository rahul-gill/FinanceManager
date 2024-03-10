package io.github.gill.rahul.financemanager.ui.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import wow.app.core.R
import io.github.gill.rahul.financemanager.models.TransactionType
import wow.app.core.ui.components.DragDropLazyColumn


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CategoryOrganizeScreen(
    onGoBack: () -> Unit,
    onUpdate: () -> Unit//TODO
) {
    var transactionType: TransactionType by remember {
        mutableStateOf(TransactionType.Expense)
    }
    val categories = remember {
        mutableStateListOf("Cat1", "Cat2", "Cat3", "Cat4", "Cat5")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.create_new_transaction)) },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back_screen)
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                TransactionType.entries.fastForEachIndexed { index, txnType ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = TransactionType.entries.size
                        ),
                        onClick = { transactionType = txnType },
                        selected = transactionType == txnType
                    ) {
                        Text(
                            text = stringResource(
                                id = when (txnType) {
                                    TransactionType.Income -> R.string.income
                                    TransactionType.Expense -> R.string.expense
                                }
                            )
                        )
                    }
                }
                val lazyListState = rememberLazyListState()


                DragDropLazyColumn(items = categories, onSwap = { from, to ->
                    categories.add(to, categories.removeAt(from))
                    onUpdate()
                }) { item ->
                    Card(
                        modifier = Modifier
                            .clickable {  },
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

//                val reorderableLazyColumnState =
//                    rememberReorderableLazyColumnState(lazyListState) { from, to ->
//                        categories.add(to.index, categories.removeAt(from.index))
//                        onUpdate()
//                    }
//
//                LazyColumn(state = lazyListState) {
//                    items(categories, key = { it }) { item ->
//                        ReorderableItem(reorderableLazyColumnState, key = { item }) { isDragging ->
//                            Row(Modifier.fillMaxWidth()) {
//                                Text(text = item, modifier = Modifier.weight(1f))
//                                IconButton(
//                                    modifier = Modifier
//                                        .aspectRatio(1f)
//                                        .background(MaterialTheme.colorScheme.surface)
//                                        .minimumInteractiveComponentSize()
//                                        .draggableHandle(),
//                                    onClick = {}
//
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.DragHandle,
//                                        contentDescription = "TODO"
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }

            }
        }
    }
}

@Composable
@Preview
private fun CategoryOrganizeScreenPreview() {
    CategoryOrganizeScreen(onGoBack = {}, onUpdate = {})
}
