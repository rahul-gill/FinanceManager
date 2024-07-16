package io.github.gill.rahul.financemanager.ui.screen.accounts

import android.os.Build
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.gill.rahul.financemanager.db.CategoriesOperations
import io.github.gill.rahul.financemanager.models.AccountUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyColumnState
import wow.app.core.ui.getContentColorForBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AccountsListScreen(
    navigateUp: () -> Unit,
    onAccountClick: (accountId: Long) -> Unit,
    accounts: List<AccountUiModel>,
    navigateToCreateAccount: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Accounts")
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "TODO"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = navigateToCreateAccount) {
                Text(text = "Create a new account")
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

            val list = remember(accounts) {
                accounts.toMutableStateList()
            }

            if (list.isNotEmpty()) {
                Text(
                    text = "Long press or use the hande to reorder accounts",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            val haptics = LocalHapticFeedback.current
            val reorderableLazyColumnState =
                rememberReorderableLazyColumnState(listState) { from, to ->
                    val fromItem = list.removeAt(from.index)
                    list.add(to.index, fromItem)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }

            if (list.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "No account created yet. Create one now!",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )

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
                            DraggableAccountItem(
                                isDragging = isDragging,
                                onAccountClick = { onAccountClick(item.id) },
                                name = item.name,
                                color = item.color,
                                iconRes = item.iconRes,
                                reorderHandle = {
                                    IconButton(
                                        modifier = Modifier.draggableHandle(
                                            onDragStarted = {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                }
                                            },
                                            onDragStopped = {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                }
                                                scope.launch {
                                                    delay(100)
                                                    CategoriesOperations.instance.updateCategoryOrdering(
                                                        list.mapIndexed { index, item ->
                                                            Pair(
                                                                item.id,
                                                                index
                                                            )
                                                        })
                                                }
                                            },
                                        ),
                                        onClick = {},
                                    ) {
                                        Icon(
                                            Icons.Rounded.DragHandle,
                                            contentDescription = "Reorder"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraggableAccountItem(
    isDragging: Boolean,
    onAccountClick: () -> Unit,
    name: String,
    color: Color,
    @DrawableRes
    iconRes: Int,
    reorderHandle: @Composable () -> Unit
){
    val elevation by animateDpAsState(
        if (isDragging) 20.dp else 1.dp,
        label = "Elevation"
    )
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        onClick = onAccountClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(25)
                    )
                    .padding(4.dp)
                    .size(24.dp),
                painter = run {
                    painterResource(id = iconRes)
                },
                contentDescription = null,
                tint = getContentColorForBackground(color)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 20.dp)
            )
            reorderHandle()
        }
    }
}