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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import io.github.gill.rahul.financemanager.ui.MoneyManagerPreviews
import io.github.gill.rahul.financemanager.ui.getContentColorForBackground
import io.github.gill.rahul.financemanager.ui.getHexString
import io.github.gill.rahul.financemanager.ui.onCondition

val colorsArray = listOf(
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
@Composable
@MoneyManagerPreviews
fun ColorSelection(
    modifier:Modifier = Modifier,
    color: Color = Color.Red,
    setColor: (Color) -> Unit = {},
    showColorPicker: () -> Unit = {}
) = Column {
    Row(
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
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
        TextButton(onClick = showColorPicker) {
            Text(text = "Select Custom")
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(50.dp),
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

@Composable
fun CustomColorPickerDialog(
    onDismiss: () -> Unit,
    initialColor: Color,
    onColorSelection: (Color) -> Unit
){
    val controller = rememberColorPickerController()
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onColorSelection(controller.selectedColor.value)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
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
                    initialColor = initialColor
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