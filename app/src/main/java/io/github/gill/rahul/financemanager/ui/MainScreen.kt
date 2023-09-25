package io.github.gill.rahul.financemanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import io.github.gill.rahul.financemanager.ui.destinations.SomeOtherScreenDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navController: NavController) {
    Surface(Modifier.fillMaxSize()) {
        Text(text = "Home", style = MaterialTheme.typography.displayLarge)
        Button(onClick = { navController.navigate(SomeOtherScreenDestination) }) {
            Text(text = "Go to that screen")
        }
    }
}

@Destination
@Composable
fun BudgetScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue) {
        Text(text = "BudgetScreen", style = MaterialTheme.typography.displayLarge)
    }
}

@Destination
@Composable
fun StatsScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Yellow) {
        Text(text = "StatsScreen", style = MaterialTheme.typography.displayLarge)
    }
}

@Destination
@Composable
fun MoreSettingsScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Red) {
        Text(text = "MoreSettingsScreen", style = MaterialTheme.typography.displayLarge)
    }
}

@Destination
@Composable
fun SomeOtherScreen() {
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
