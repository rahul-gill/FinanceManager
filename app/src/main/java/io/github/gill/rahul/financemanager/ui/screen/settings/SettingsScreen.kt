package io.github.gill.rahul.financemanager.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.FormatLineSpacing
import androidx.compose.material.icons.filled.ShortText
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import io.github.gill.rahul.financemanager.destinations.CategoryScreenDestination
import io.github.gill.rahul.financemanager.destinations.CreateAccountScreenDestination


@Destination
@Composable
fun MoreSettingsScreen(
    navController: NavController
) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.CreditCard, contentDescription = null)
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Accounts",
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    // TODO: actual balance and labels like lacs, 100k
                    Text(
                        text = "Overall: $1000"
                    )
                }
                TextButton(onClick = { navController.navigate(CreateAccountScreenDestination) }) {
                    Text(text = "Add New")
                }
            }
            LazyRow {
                items(5) {
                    // TODO accounts listing
                    AccountCard(Modifier.padding(horizontal = 4.dp))
                }
            }
        }
        item {
            Text(
                text = "Setting",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        item {
            SettingsItem(
                modifier = Modifier.padding(vertical = 4.dp),
                icon = Icons.Default.ShortText,
                title = "Categories",
                description = "Add, edit or reorder categories",
                onClick = { navController.navigate(CategoryScreenDestination) }
            )
        }
        items(5) {
            SettingsItem(Modifier.padding(vertical = 4.dp))
        }
        item {
            Text(
                text = "About",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        items(5) {
            SettingsItem(Modifier.padding(vertical = 4.dp))
        }
    }
}

const val SecondaryTextAlpha = 0.7f

@Preview(showBackground = true)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountIcon: ImageVector = Icons.Default.Wallet,
    accountName: String = "Main Account",
    accountBalanceString: String = "$100k"
) {
    OutlinedCard(modifier) {
        Row(
            modifier = Modifier
                .width(150.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = accountBalanceString,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = accountName,
                    style = MaterialTheme.typography.bodyMedium.run {
                        copy(color = this.color.copy(alpha = SecondaryTextAlpha))
                    }
                )
            }
            Icon(imageVector = accountIcon, contentDescription = null)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsItem(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.FormatLineSpacing,
    title: String = "Categories",
    description: String = "Add, edit or reorder categories",
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.then(Modifier.clickable { onClick() }),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
