package io.github.gill.rahul.financemanager.ui.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

@SuppressLint("DiscouragedApi")
@Composable
@ReadOnlyComposable
@DrawableRes
fun getIconForIconName(
    iconName: String
): Int {
    LocalConfiguration.current
    return LocalContext.current.run {
        resources.getIdentifier(iconName, "string", packageName)
    }
}
