package wow.app.core.ui

import android.content.res.Configuration
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import io.github.gill.rahul.financemanager.ui.theme.DarkColors
import io.github.gill.rahul.financemanager.ui.theme.FinanceManagerTheme
import io.github.gill.rahul.financemanager.ui.theme.LightColors

private const val AnimationDuration = 250

fun materialSharedAxisZIn(
    forward: Boolean,
    durationMillis: Int = AnimationDuration,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis.ForIncoming,
        delayMillis = durationMillis.ForOutgoing,
        easing = LinearOutSlowInEasing
    )
) + scaleIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    initialScale = if (forward) 0.8f else 1.1f
)

fun materialSharedAxisZOut(
    forward: Boolean,
    durationMillis: Int = AnimationDuration,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis.ForOutgoing,
        delayMillis = 0,
        easing = FastOutLinearInEasing
    )
) + scaleOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    targetScale = if (forward) 1.1f else 0.8f
)

private const val ProgressThreshold = 0.35f

private val Int.ForOutgoing: Int
    get() = (this * ProgressThreshold).toInt()

private val Int.ForIncoming: Int
    get() = this - this.ForOutgoing


private const val LuminanceThreshold = 0.5f

fun Modifier.onCondition(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) this.block() else this
}


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
