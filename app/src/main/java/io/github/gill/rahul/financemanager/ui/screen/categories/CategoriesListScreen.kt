package io.github.gill.rahul.financemanager.ui.screen.categories

import android.os.Build
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import io.github.gill.rahul.financemanager.db.CategoriesOperations
import io.github.gill.rahul.financemanager.models.TransactionType
import io.github.gill.rahul.financemanager.models.CategoryUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyColumnState
import wow.app.core.R
import wow.app.core.ui.getContentColorForBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CategoriesListScreen(
    navigateUp: () -> Unit,
    onCategoryClick: (categoryId: Long) -> Unit,
    expenseCategories: List<CategoryUiModel>,
    incomeCategories: List<CategoryUiModel>,
    onSaveCategoriesOrder: (from: Int,to: Int) -> Unit,
    navigateToCreateCategory: () -> Unit
) {
    var categoryType by remember {
        mutableStateOf(TransactionType.Expense)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Categories")
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "TODO")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { navigateToCreateCategory() }) {
                Text(text = "Create a new category")
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {

                TransactionType.entries.fastForEachIndexed { index, txnType ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = TransactionType.entries.size
                        ),
                        onClick = { categoryType = txnType },
                        selected = categoryType == txnType
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
            }

            val list = remember(categoryType, incomeCategories, expenseCategories) {
                (if (categoryType == TransactionType.Income) incomeCategories else expenseCategories)
                    .toMutableStateList()
            }

            if(list.isNotEmpty()){
                Text(
                    text = "Long press or use the hande to reorder categories",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            val haptics = LocalHapticFeedback.current
            val reorderableLazyColumnState = rememberReorderableLazyColumnState(listState) { from, to ->
                val fromItem = list.removeAt(from.index)
                list.add(to.index, fromItem)
                println("43243 list modified as ${list.map { it.name }}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                println("4324232 onDragStopped")
            }

            if(list.isEmpty()){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ){
                    Text(text = "No category created yet. Create one now!", style = MaterialTheme.typography.labelMedium,  textAlign = TextAlign.Center)

                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(list, key = { _, item -> item.id }) { index, item ->
                        ReorderableItem(reorderableLazyColumnState, key = item.id) { isDragging ->
                            val elevation by animateDpAsState(
                                if (isDragging ) 20.dp else 1.dp,
                                label = "Elevation"
                            )
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                                modifier = Modifier
                                    .clickable {
                                        onCategoryClick(item.id)
                                    }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .background(
                                                color = item.color,
                                                shape = RoundedCornerShape(25)
                                            )
                                            .padding(4.dp)
                                            .size(24.dp),
                                        painter = run {
                                            painterResource(id = item.iconRes)
                                        },
                                        contentDescription = null,
                                        tint = getContentColorForBackground(item.color)
                                    )
                                    Text(
                                        text = item.name,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 20.dp)
                                    )
                                    IconButton(
                                        modifier = Modifier.draggableHandle(
                                            onDragStarted = {
                                                println("4324232 onDragStarted")
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                }
                                            },
                                            onDragStopped = {
                                                println("4324232 onDragStopped")
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                }
                                                scope.launch {
                                                    delay(100)
                                                    CategoriesOperations.instance.updateCategoryOrdering(list.mapIndexed{ index, item -> Pair(item.id, index) })
                                                }
                                            },
                                        ),
                                        onClick = {},
                                    ) {
                                        Icon(Icons.Rounded.DragHandle, contentDescription = "Reorder")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}