package io.github.gill.rahul.financemanager.ui.screen.settings

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onSave: () -> Unit,
    navigateUp: () -> Unit
) {
    //TODO
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add new Account")
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "TODO")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "TODO")
            }
        }
    ) { paddingValues ->

        var colorPickerShowing by remember {
            mutableStateOf(false)
        }
        val (amount, setAmount) = remember {
            mutableStateOf("0")
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
        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            columns = GridCells.Adaptive(minSize = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
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
                //TODO
//                ColorSelection(
//                    color = color,
//                    showColorPicker = {
//                        colorPickerShowing = true
//                    },
//                    setColor = setColor
//                )
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
                        //TODO: tint = getContentColorForBackground(color)
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
                        val backColor = remember(categoryKey, iconCategoryKey, thisIconKey, iconKey) {
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
                            //TODO: tint = getContentColorForBackground(backColor)
                        )
                    }
                }
            }
            item(span = fullLineSpan) {
                Spacer(Modifier.height(100.dp))
            }
        }
        if(colorPickerShowing){
            //TODO:
//            CustomColorPickerDialog(
//                onDismiss = { colorPickerShowing = false },
//                initialColor = color,
//                onColorSelection = setColor,
//            )
        }
    }
}