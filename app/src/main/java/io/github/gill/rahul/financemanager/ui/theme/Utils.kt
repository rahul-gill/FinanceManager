package io.github.gill.rahul.financemanager.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview

private const val LuminanceThreshold = 0.5f

fun Color.getHexString(): String {
    return String.format("#%06X", 0xFFFFFF and this.toArgb())
}
fun getContentColorForBackground(
    color: Color
): Color {
    val darkerColor = color.luminance() > LuminanceThreshold
    return if (darkerColor) LightColors.onBackground else DarkColors.onBackground
}

@Preview(
    name = "dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "light mode"
)
annotation class MoneyManagerPreviews

@Composable
fun PreviewWrapper(content: @Composable () -> Unit) {
    FinanceManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
