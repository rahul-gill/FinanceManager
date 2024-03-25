package io.github.gill.rahul.financemanager.ui.screen.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.gill.rahul.financemanager.ui.screen.settings.ColorSelection
import io.github.gill.rahul.financemanager.ui.screen.settings.CustomColorPickerDialog
import io.github.gill.rahul.financemanager.ui.screen.settings.IconsMap
import io.github.gill.rahul.financemanager.ui.screen.settings.colorsArray
import kotlinx.coroutines.launch
import wow.app.core.ui.getContentColorForBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    navigateUp: () -> Unit,
    onSave: (name: String, color: Color, iconIdentifier: String, startingBalance: Long, includeInTotalBalance: Boolean) -> Unit,

    ) {

    var colorPickerShowing by remember {
    mutableStateOf(false)
}
    val (title, setTitle) = remember {
        mutableStateOf("")
    }
    val (color, setColor) = remember {
        mutableStateOf(colorsArray[0])
    }
    var iconCategoryKey: String by remember {
        mutableStateOf("medical")
    }
    var iconKey: String by remember {
        mutableStateOf("heartbeat")
    }
    val fullLineSpan: LazyGridItemSpanScope.() -> GridItemSpan = remember {
        { GridItemSpan(maxLineSpan) }
    }

    val (amount, setAmount) = remember {
        mutableStateOf("0")
    }
    var includeInBalance by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create new account")
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
            FloatingActionButton(
                onClick = {
                    if (title.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Account name cannot be empty",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        onSave(title, color, "$iconCategoryKey//$iconKey", (amount.toDoubleOrNull()?.times(100) ?: 0).toLong(), includeInBalance)
                        navigateUp()
                    }
                }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "TODO")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            columns = GridCells.Adaptive(minSize = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = fullLineSpan) {
                OutlinedTextField(
                    value = title,
                    onValueChange = setTitle,
                    label = { Text(text = "Account Name") },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            item(span = fullLineSpan) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = setAmount,
                    textStyle = MaterialTheme.typography.titleLarge,
                    label = { Text(text = "Initial Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
            }
            item(span = fullLineSpan) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(25))
                        .background(shape = RoundedCornerShape(25), color = Color.Transparent)
                        .toggleable(
                            value = includeInBalance,
                            onValueChange = { includeInBalance = !includeInBalance },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = includeInBalance,
                        onCheckedChange = null
                    )
                    Text(
                        text = "Include this account's balance in total balance",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            item(span = fullLineSpan) {
                //TODO
                ColorSelection(
                    color = color,
                    showColorPicker = {
                        colorPickerShowing = true
                    },
                    setColor = setColor
                )
            }
            item(span = fullLineSpan) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(text = "Icon", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .background(color = color, shape = RoundedCornerShape(25))
                            .padding(4.dp)
                            .size(24.dp),
                        painter = painterResource(id = IconsMap.collection[iconCategoryKey]!![iconKey]!!),
                        contentDescription = null,
                        tint = getContentColorForBackground(color)
                    )
                }
            }
            IconsMap.collection.forEach { (categoryKey, categoryIcons) ->
                item(span = fullLineSpan) {
                    Text(text = categoryKey)
                }
                categoryIcons.forEach { (thisIconKey, resValue) ->
                    item(key = thisIconKey) {
                        val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
                        val backColor =
                            remember(categoryKey, iconCategoryKey, thisIconKey, iconKey, color) {
                                val sameCategory = iconCategoryKey == categoryKey
                                val sameIcon = iconKey == thisIconKey
                                if (sameCategory && sameIcon)
                                    color else surfaceVariant
                            }
                        Icon(
                            modifier = Modifier
                                .background(
                                    color = backColor,
                                    shape = RoundedCornerShape(25)
                                )
                                .padding(8.dp)
                                .clickable {
                                    iconCategoryKey = categoryKey
                                    iconKey = thisIconKey
                                },
                            painter = painterResource(resValue),
                            contentDescription = null,
                            tint = getContentColorForBackground(backColor)
                        )
                    }
                }
            }
            item(span = fullLineSpan) {
                Spacer(Modifier.height(100.dp))
            }
        }
        if (colorPickerShowing) {
            //TODO:
            CustomColorPickerDialog(
                onDismiss = { colorPickerShowing = false },
                initialColor = color,
                onColorSelection = setColor,
            )
        }
    }
}