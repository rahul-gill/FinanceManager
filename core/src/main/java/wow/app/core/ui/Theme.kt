package wow.app.core.ui

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.materialkolor.dynamicColorScheme

enum class DarkThemeType {
    Dark, Black
}

enum class ThemeConfig {
    FollowSystem, Light, Dark
}

sealed interface ColorSchemeType {
    class WithSeed(val seed: Color = Color.Blue) : ColorSchemeType
    data object Dynamic : ColorSchemeType
}

private val DefaultSeed = Color.Magenta


@Composable
fun FinManTheme(
    colorSchemeType: ColorSchemeType = ColorSchemeType.Dynamic,
    themeConfig: ThemeConfig = ThemeConfig.FollowSystem,
    darkThemeType: DarkThemeType = DarkThemeType.Dark,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme =
        themeConfig == ThemeConfig.Dark || (themeConfig == ThemeConfig.FollowSystem && isSystemInDarkTheme())
    val colorScheme = remember(colorSchemeType, isDarkTheme, darkThemeType) {
        val scheme = when (colorSchemeType) {
            ColorSchemeType.Dynamic -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (isDarkTheme)
                        dynamicDarkColorScheme(context)
                    else
                        dynamicLightColorScheme(context)
                } else {
                    dynamicColorScheme(DefaultSeed, isDarkTheme)
                }
            }

            is ColorSchemeType.WithSeed -> {
                dynamicColorScheme(colorSchemeType.seed, isDarkTheme)
            }
        }
        scheme.copy(
            background = if (isDarkTheme && darkThemeType == DarkThemeType.Black) Color.Black else scheme.background,
            surface = if (isDarkTheme && darkThemeType == DarkThemeType.Black) Color.Black else scheme.surface
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )

}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
private fun FinManThemePreview() {
    var isDarkTheme by remember {
        mutableStateOf(ThemeConfig.FollowSystem)
    }
    var darkThemeType by remember {
        mutableStateOf(DarkThemeType.Dark)
    }
    var seed by remember {
        mutableStateOf(Color.Magenta)
    }
    FinManTheme(
        themeConfig = isDarkTheme,
        darkThemeType = darkThemeType,
        colorSchemeType = ColorSchemeType.WithSeed(seed = seed)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(title = { Text("App Name") })
                }
            ) { values ->
                Column(
                    modifier = Modifier
                        .padding(values)
                        .verticalScroll(rememberScrollState())
                ) {
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            isDarkTheme = when (isDarkTheme) {
                                ThemeConfig.FollowSystem -> ThemeConfig.Light
                                ThemeConfig.Light -> ThemeConfig.Dark
                                else -> ThemeConfig.FollowSystem
                            }
                        }) {
                            Text(text = "SwitchThemeConfig")
                        }
                        Button(onClick = {
                            darkThemeType = when (darkThemeType) {
                                DarkThemeType.Dark -> DarkThemeType.Black
                                DarkThemeType.Black -> DarkThemeType.Dark
                            }
                        }) {
                            Text(text = "DarkThemeType")
                        }
                        Button(onClick = {
                            seed = when (seed) {
                                Color.Magenta -> Color.Blue
                                Color.Blue -> Color.Yellow
                                Color.Yellow -> Color.Cyan
                                Color.Cyan -> Color.Green
                                Color.Green -> Color.Red
                                else -> Color.Magenta
                            }
                        }) {
                            Text(text = "SwitchSeed")
                        }
                    }
                    for (i in 0..10) {
                        Card(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                            Text("Some Text", modifier = Modifier.padding(12.dp))
                        }
                        Button(onClick = {}) {
                            Text("Some Text")
                        }
                    }
                }
            }
        }
    }
}