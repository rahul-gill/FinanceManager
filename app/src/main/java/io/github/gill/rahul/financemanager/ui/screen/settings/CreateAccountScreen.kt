package io.github.gill.rahul.financemanager.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatShapes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.ramcosta.composedestinations.annotation.Destination
import io.github.gill.rahul.financemanager.ui.theme.MoneyManagerPreviews
import io.github.gill.rahul.financemanager.ui.theme.PreviewWrapper
import io.github.gill.rahul.financemanager.ui.theme.getContentColorForBackground
import io.github.gill.rahul.financemanager.ui.theme.getHexString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun CreateAccountScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Add new Account") }, navigationIcon = {
                IconButton(onClick = navController::navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "TODO")
                }
            })
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
        val colorsArray = remember {
            mutableStateListOf(
                Color(0xFF2196F3), // Blue
                Color(0xFF4CAF50), // Green
                Color(0xFFFFC107), // Yellow
                Color(0xFFF44336), // Red
                Color(0xFF9C27B0), // Purple
                Color(0xFFE91E63), // Pink
                Color(0xFF795548), // Brown
                Color(0xFF607D8B), // Gray
                Color(0xFF009688), // Teal
                Color(0xFF00BCD4), // Cyan
                Color(0xFF673AB7), // Deep Purple
                Color(0xFF3F51B5), // Indigo
                Color(0xFFFF5722), // Deep Orange
                Color(0xFFCDDC39), // Lime
                Color(0xFF9E9E9E) // Grey
            )
        }
        val (color, setColor) = remember {
            mutableStateOf(colorsArray[0])
        }
        Column(Modifier.padding(paddingValues)) {

            OutlinedTextField(
                value = title,
                onValueChange = setTitle,
                label = { Text(text = "Account Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
            OutlinedTextField(
                value = amount,
                onValueChange = setAmount,
                textStyle = MaterialTheme.typography.titleLarge,
                label = { Text(text = "Initial Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = "Color", style = MaterialTheme.typography.titleMedium)
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(26.dp),
                    color = color,
                    shape = RoundedCornerShape(25)
                ) {}
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { colorPickerShowing = true }) {
                    Text(text = "Select Custom")
                }
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
                modifier = Modifier.height(158.dp)
            ) {
                colorsArray.forEach { colorItem ->
                    item(key = colorItem.toArgb()) {
                        ColorSelectorItem(
                            modifier = Modifier
                                .padding(8.dp),
                            color = colorItem,
                            isSelected = colorItem == color,
                            onClick = {
                                setColor(colorItem)
                            }
                        )
                    }
                }
            }
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
                    imageVector = Icons.Default.FormatShapes,
                    contentDescription = null,
                    tint = getContentColorForBackground(color)
                )
            }
        }
        if (colorPickerShowing) {
            val controller = rememberColorPickerController()
            AlertDialog(
                onDismissRequest = { colorPickerShowing = false },
                confirmButton = {
                    TextButton(onClick = {
                        setColor(controller.selectedColor.value)
                        colorPickerShowing = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        colorPickerShowing = false
                    }) {
                        Text(text = "Cancel")
                    }
                },
                text = {
                    Column {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = { },
                            initialColor = color
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(
                                    color = controller.selectedColor.value,
                                    shape = RoundedCornerShape(25)
                                ),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(
                                text = controller.selectedColor.value.getHexString(),
                                modifier = Modifier.padding(16.dp),
                                color = getContentColorForBackground(controller.selectedColor.value)
                            )
                        }
                    }
                }
            )
        }
    }
}

@MoneyManagerPreviews
@Composable
private fun ColorSelectorItemPreview() {
    PreviewWrapper {
        Column(Modifier.size(200.dp)) {
            CreateAccountScreen(NavController(LocalContext.current))
        }
    }
}

@Composable
fun ColorSelectorItem(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    isSelected: Boolean = true,
    onClick: () -> Unit = {}
) {
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .then(modifier)
            .clickable(onClick = onClick)
            .onCondition(isSelected) {
                border(
                    width = 1.dp,
                    color = onSurfaceColor,
                    shape = RoundedCornerShape(25)
                )
            }

    ) {
        Surface(
            modifier = Modifier
                .size(34.dp)
                .padding(4.dp),
            color = color,
            shape = RoundedCornerShape(25)
        ) {}
    }
}

fun Modifier.onCondition(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) this.block() else this
}
