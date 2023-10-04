package io.github.gill.rahul.financemanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun BudgetScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue) {
        Text(text = "BudgetScreen", style = MaterialTheme.typography.displayLarge)
    }
}
