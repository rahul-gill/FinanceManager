package wow.app.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import wow.app.core.ui.FinManTheme


private val GroupHeaderStartPadding = 16.dp
private const val GroupHeaderFontSizeMultiplier = 0.85f

@Composable
fun GroupHeader(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        Modifier
            .padding(start = GroupHeaderStartPadding)
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            title,
            color = color,
            fontSize = LocalTextStyle.current.fontSize.times(GroupHeaderFontSizeMultiplier),
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun SwitchPreference(
    title: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    summary: String? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    isEnabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholderForIcon: Boolean = true,
    boxedListItemType: BoxedListItemType = BoxedListItemType.Single
) {
    BoxedListItem(
        modifier = Modifier
            .clickable(
                enabled = isEnabled,
                onClickLabel = "TODO",
                role = Role.Switch,
                onClick = {
                    if (onCheckedChange != null) {
                        onCheckedChange(!isChecked)
                    }
                }
            )
            .then(modifier),
        isEnabled = isEnabled,
        type = boxedListItemType
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            if (leadingIcon == null && placeholderForIcon) {
                Box(Modifier.minimumInteractiveComponentSize()) {}
            } else if (leadingIcon != null) {
                Box(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    leadingIcon()
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                if (summary != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isChecked,
                onCheckedChange = null,
                enabled = isEnabled
            )
        }
    }
}


@Composable
@Preview
private fun SwitchPreferencePreview() {
    FinManTheme {
        Column(Modifier.padding(8.dp)) {
            for (i in 0..10) {
                var isChecked by remember {
                    mutableStateOf(i % 3 == 0)
                }
                SwitchPreference(
                    title = "Some Title",
                    summary = "Some very long summary it is. I can't do much about it. Can I?",
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = !isChecked },
                    boxedListItemType = BoxedListItemType.getByListIndex(i, 11),
                    modifier = Modifier
                        .fillMaxWidth(),
                    isEnabled = i % 2 == 0,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}