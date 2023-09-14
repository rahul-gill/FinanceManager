package io.github.gill.rahul.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    Column {
        Text(text = "")
    }
}

@Destination
@Composable
fun BudgetScreen() {
    Column {
        Text(text = "")
    }
}

@Destination
@Composable
fun StatsScreen() {
    Column {
        Text(text = "")
    }
}

@Destination
@Composable
fun MoreSettingsScreen() {
    Column {
        Text(text = "")
    }
}
