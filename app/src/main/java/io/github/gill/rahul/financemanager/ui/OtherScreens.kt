package io.github.gill.rahul.financemanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun BudgetScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "BudgetScreen", style = MaterialTheme.typography.displayLarge)
        val items = remember {
            listOf("Tracks", "Albums", "Artists", "Playlists", "Very very long item check")
        }
        val (selectedItem, setSelectedItem) = remember {
            mutableIntStateOf(0)
        }
        Text("Selected: ${items[selectedItem]}")
    }
}


@Composable
fun StatsScreen(modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Yellow) {
        Text(text = "StatsScreen", style = MaterialTheme.typography.displayLarge)
    }
}



@Composable
fun SomeOtherScreen(modifier: Modifier = Modifier) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.CreditCard, contentDescription = null)
            Column {
                Text(text = "Accounts", style = MaterialTheme.typography.titleMedium)
                //TODO: amount actual value and amount format like 302k, 3 lac etc
                Text(text = "Overall: 1030")
            }
            TextButton(onClick = {}){
                Text(text = "Add new")
            }
        }
    }
}
