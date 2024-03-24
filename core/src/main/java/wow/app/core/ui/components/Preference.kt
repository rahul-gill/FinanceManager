package wow.app.core.ui.components

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import wow.app.core.R


private val GroupHeaderStartPadding = 16.dp
private const val GroupHeaderFontSizeMultiplier = 0.85f
private val PrefItemMinHeight = 72.dp

@Composable
fun PreferenceGroupHeader(
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
fun GenericPreference(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    summary: String? = null,
    placeholderSpaceForLeadingIcon: Boolean = true
) {
    Row(
        modifier = Modifier
            .clickable(
                onClickLabel = "TODO",
                onClick = onClick ?: {}
            )
            .heightIn(min = PrefItemMinHeight)
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon == null && placeholderSpaceForLeadingIcon) {
            Box(Modifier.minimumInteractiveComponentSize()) {}
        } else if (leadingIcon != null) {
            Box(
                modifier = Modifier.minimumInteractiveComponentSize().align(Alignment.Top),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
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
        if (trailingContent != null) {
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            trailingContent()
        }
    }
}


@Composable
fun <T> ListPreference(
    title: String,
    items: List<T>,
    selectedItem: T,
    onItemSelection: (T) -> Unit,
    itemToDescription: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholderForIcon: Boolean = true,
    selectItemOnClick: Boolean = true
) {
    val isShowingSelectionDialog = remember {
        mutableStateOf(false)
    }
    GenericPreference(
        title = title,
        leadingIcon = leadingIcon,
        summary = itemToDescription(selectedItem),
        modifier = modifier,
        placeholderSpaceForLeadingIcon = placeholderForIcon,
        onClick = {
            isShowingSelectionDialog.value = true
        },
    )
    if (isShowingSelectionDialog.value) {
        val dialogSelectedItem = remember {
            mutableStateOf(selectedItem)
        }
        AlertDialog(
            onDismissRequest = {
                isShowingSelectionDialog.value = false
            },
            title = {
                Text(text = title)
            },
            body = {
                Column(
                    Modifier
                        .selectableGroup()
                        .verticalScroll(rememberScrollState())
                ) {
                    items.forEach { choice ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (choice == dialogSelectedItem.value),
                                    onClick = {
                                        dialogSelectedItem.value = choice
                                        if (selectItemOnClick) {
                                            onItemSelection(choice)
                                        }
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (choice == dialogSelectedItem.value),
                                onClick = null
                            )
                            Text(
                                text = itemToDescription(choice),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            buttonBar = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { isShowingSelectionDialog.value = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        if (!selectItemOnClick) {
                            onItemSelection(dialogSelectedItem.value)
                        }
                        isShowingSelectionDialog.value = false
                    }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
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
) {
    println(" 4523423 SwitchPreference isChecked: $isChecked")
    GenericPreference(
        title = title,
        onClick = {
            if (onCheckedChange != null) {

                println(" 4523423 SwitchPreference set isChecked: ${!isChecked}")
                onCheckedChange(!isChecked)
            }
        },
        modifier = modifier,
        summary = summary,
        leadingIcon = leadingIcon,
        placeholderSpaceForLeadingIcon = placeholderForIcon,
        trailingContent = {

            println("4523423 calling Switch isChecked: $isChecked")
            Switch(
                modifier = modifier,
                checked = isChecked,
                onCheckedChange = null,
                enabled = isEnabled
            )
        })
}

