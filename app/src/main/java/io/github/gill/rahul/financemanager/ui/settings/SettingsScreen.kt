package io.github.gill.rahul.financemanager.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import wow.app.core.ui.FinManTheme


@Preview(showBackground = true, showSystemUi = true)

@Composable
fun MoreSettingsScreen() {
    FinManTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val overallBalance = "$ 10000"
            val onAddAccount = {}
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Card {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                        Icon(
                            imageVector = Icons.Default.Wallet, contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .padding(12.dp)
                                .background(Color.Transparent)
                        )
                        Column(Modifier.weight(1f)) {
                            Text(text = "Accounts", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Overall balance: $overallBalance",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        TextButton(onClick = onAddAccount) {
                            Text("Add New")
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(10) {
                        AccountCard()
                    }
                }
            }
        }
    }
}


@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    color: Color = Color.Magenta,
    icon: Painter = rememberVectorPainter(Icons.Default.AccountBalanceWallet),
    isDefault: Boolean = true
) {
    OutlinedCard(
        modifier = Modifier
            .width(200.dp)
            .then(modifier),
        border = BorderStroke(width = 1.dp, color = color)
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .padding(4.dp),
                    tint = color
                )
                Text(text = "Main Account")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = "- $ 10,49000.48",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                if (isDefault) {
                    Text(text = "Default", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}