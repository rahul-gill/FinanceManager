package io.github.gill.rahul.financemanager.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SubdirectoryArrowLeft
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import com.ramcosta.composedestinations.annotation.Destination
import io.github.gill.rahul.financemanager.ui.theme.MoneyManagerPreviews
import io.github.gill.rahul.financemanager.ui.theme.PreviewWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun CreateTransactionScreen() {
    val accountIcon by remember {
        mutableStateOf(Icons.Default.Wallet)
    }
    val accountName by remember {
        mutableStateOf("Main Account")
    }
    val onGoBack = remember { {} }
    val onChooseAccount = remember { {} }

    var isExpenseTransaction by remember {
        mutableStateOf(false)
    }
    val (amount, setAmount) = remember {
        mutableStateOf("")
    }
    val (title, setTitle) = remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxSize()) {
        TopBar(
            onGoBack = onGoBack,
            accountIcon = accountIcon,
            accountName = accountName,
            onChooseAccount = onChooseAccount
        )
        Row {
            ElevatedFilterChip(
                selected = !isExpenseTransaction,
                onClick = { isExpenseTransaction = false },
                label = { Text(text = "Income") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.SubdirectoryArrowLeft,
                        contentDescription = null
                    )
                }
            )
            ElevatedFilterChip(
                selected = isExpenseTransaction,
                onClick = { isExpenseTransaction = true },
                label = { Text(text = "Expense") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.SubdirectoryArrowRight,
                        contentDescription = null
                    )
                }
            )
        }
        Row {
            AssistChip(
                onClick = { /*TODO*/ },
                label = { Text(text = "Sat Oct 7, 2023") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null
                    )
                }
            )
            AssistChip(
                onClick = { /*TODO*/ },
                label = { Text(text = "5:45 pm") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null
                    )
                }
            )
        }
        BasicTextField(
            value = amount,
            onValueChange = setAmount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        OutlinedTextField(value = title, onValueChange = setTitle, label = { Text(text = "Title") })
        CategoryChooser()
    }
}

@Composable
fun CategoryChooser() {
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    accountIcon: ImageVector,
    accountName: String,
    onChooseAccount: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomStartPercent = 50,
            bottomEndPercent = 50
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onGoBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "TODO")
            }
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onTertiary
                ) {
                    Icon(imageVector = accountIcon, contentDescription = "TODO")
                }
            }
            Text(text = accountName, style = MaterialTheme.typography.titleLarge)
            IconButton(
                onClick = onChooseAccount,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "TODO")
            }
        }
    }
}

@Composable
@MoneyManagerPreviews
private fun CreateTransactionScreenPreview() {
    PreviewWrapper {
        CreateTransactionScreen()
    }
}
