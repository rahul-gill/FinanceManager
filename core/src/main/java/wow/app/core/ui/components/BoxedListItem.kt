package wow.app.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


enum class BoxedListItemType {
    Single, First, Last, Middle;

    companion object {
        fun getByListIndex(index: Int, size: Int): BoxedListItemType {
            return if (size == 1) Single
            else if (index == 0) First
            else if (index == size - 1) Last
            else Middle
        }

    }
}

const val DisabledContentOpacity = 0.38f

@Composable
fun BoxedListItem(
    modifier: Modifier = Modifier,
    type: BoxedListItemType,
    isEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = if (isEnabled) 1f else DisabledContentOpacity)
        ),
        shape = when (type) {
            BoxedListItemType.Single -> RoundedCornerShape(25)
            BoxedListItemType.First -> RoundedCornerShape(25, 25, 0, 0)
            BoxedListItemType.Last -> RoundedCornerShape(0, 0, 25, 25)
            BoxedListItemType.Middle -> RoundedCornerShape(0)
        }
    ) {
        this.content()
    }
}


@Composable
@Preview
private fun BoxedListItemPreview() {
    Column(Modifier.padding(vertical = 8.dp)) {
        for (i in 0..10) {
            BoxedListItem(
                type = BoxedListItemType.getByListIndex(i, 11),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                isEnabled = i % 2 == 0
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Something You wanna Say")
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}