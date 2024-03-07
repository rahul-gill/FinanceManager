package io.github.gill.rahul.financemanager.ui.categories

import android.provider.Contacts.Intents.UI
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.SouthAmerica
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import wow.app.core.ui.DarkThemeType
import wow.app.core.ui.FinManTheme
import wow.app.core.ui.ThemeConfig
import wow.app.core.ui.materialSharedAxisZIn
import wow.app.core.ui.materialSharedAxisZOut
import kotlin.random.Random


@Composable
fun CategoryChip(
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    color: Color,
    title: String,
    isEnabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                shape = RoundedCornerShape(50)
            )
            .background(
                color = if (isSelected) color else Color.Unspecified,
                shape = RoundedCornerShape(50)

            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                role = Role.RadioButton,
                onClick = onClick,
                enabled = isEnabled
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = remember(color) {
            if (color.luminance() <= 0.49) {
                Color.White
            } else {
                Color.Black
            }
        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                    shape = RoundedCornerShape(50)
                )
                .background(
                    color = color,
                    shape = CircleShape
                )
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = contentColor,

                )
        }
        Text(
            text = title,
            modifier = Modifier.padding(start = 4.dp, end = 8.dp),
            color = if (isSelected) contentColor else Color.Unspecified
        )

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    categories: List<UICategory>,
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit
) {
    //TODO: make it a preference item
    val compactCategoriesCount = 5
    var showOnlySomeCategories by remember {
        mutableStateOf(true)
    }
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (index in categories.indices) {
            AnimatedVisibility(
                visible = !showOnlySomeCategories || index < compactCategoriesCount,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CategoryChip(
                        isSelected = selectedCategoryIndex == index,
                        onClick = { onCategorySelected(index) },
                        color = categories[index].color,
                        title = categories[index].title,
                        icon = categories[index].icon
                    )
                }
            }

        }
        TextButton(onClick = { showOnlySomeCategories = !showOnlySomeCategories }) {
            Text(text = if (showOnlySomeCategories) "Show more" else "Show less")
            Icon(
                imageVector = if (showOnlySomeCategories) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }
}

data class UICategory(
    val title: String,
    val icon: Painter,
    val color: Color,
)

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun CategoryChipPreview() {
    FinManTheme(
        themeConfig = ThemeConfig.Dark,
        darkThemeType = DarkThemeType.Black
    ) {
        var checkedIndex by remember {
            mutableIntStateOf(0)
        }
        CategorySelector(
            categories = listOf(
                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Transportation",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Bills and Utilities",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.secondary
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.secondary
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.secondary
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.primary
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.primary
                ),

                UICategory(
                    title = "Others",
                    icon = rememberVectorPainter(image = Icons.Default.Favorite),
                    color = MaterialTheme.colorScheme.primary
                ),
            ),
            selectedCategoryIndex = checkedIndex,
            onCategorySelected = { checkedIndex = it }
        )
    }
}
