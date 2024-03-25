package io.github.gill.rahul.financemanager.ui.screen.txn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import wow.app.core.R
import io.github.gill.rahul.financemanager.models.TransactionType
import io.github.gill.rahul.financemanager.ui.screen.categories.CategoryChipPreview
import wow.app.core.util.DateTimeUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
@Preview(showBackground = true, locale = "hi")
private fun CreateTxnScreenPreview() {
    CreateTxnScreen({})

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateTxnScreen(
    onGoBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }
    var isTimePickerVisible by remember {
        mutableStateOf(false)
    }

    var dateTime: LocalDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }


    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(id = R.string.create_new_transaction)) },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back_screen)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            ElevatedCard(
                modifier = Modifier.imePadding(),
                shape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    var checked by remember { mutableStateOf(true) }
                    Text(
                        text = stringResource(R.string.batch_add),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        modifier = Modifier.semantics { contentDescription = "TODO" },
                        checked = checked,
                        onCheckedChange = { checked = it })
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.DoneAll, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(R.string.done))
                    }
                }
            }

        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)

    ) { paddingValues ->

        var transactionType: TransactionType by remember {
            mutableStateOf(TransactionType.Expense)
        }

        val dayFormat = stringResource(id = R.string.format_day)
        val timeFormat = stringResource(R.string.time_format_12h)
        val dateText =
            remember(dateTime) { DateTimeFormatter.ofPattern(dayFormat).format(dateTime) }
        val timeText =
            remember(dateTime) { DateTimeFormatter.ofPattern(timeFormat).format(dateTime) }
        var amount by remember { mutableStateOf("") }
        val titleFocusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current
        var title by remember { mutableStateOf("") }
        var note by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {

                TransactionType.entries.fastForEachIndexed { index, txnType ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = TransactionType.entries.size
                        ),
                        onClick = { transactionType = txnType },
                        selected = transactionType == txnType
                    ) {
                        Text(
                            text = stringResource(
                                id = when (txnType) {
                                    TransactionType.Income -> R.string.income
                                    TransactionType.Expense -> R.string.expense
                                }
                            )
                        )
                    }

                }
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AssistChip(
                    onClick = { isDatePickerVisible = true },
                    label = {
                        Text(
                            text = dateText,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    shape = RoundedCornerShape(50),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                )
                AssistChip(
                    onClick = { isTimePickerVisible = true },
                    label = {
                        Text(
                            text = timeText,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    shape = RoundedCornerShape(50),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .padding(vertical = 8.dp)
                        )
                    }
                )
            }
            TextField(
                value = amount,
                onValueChange = {
                    val double = it.toDoubleOrNull()
                    if (double != null && double >= 0) {
                        amount = it
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Calculate, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    titleFocusRequester.requestFocus()
                }),
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                placeholder = { Text(stringResource(R.string.enter_amount)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    errorIndicatorColor = MaterialTheme.colorScheme.error,
                )
            )
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title)) },
                textStyle = MaterialTheme.typography.titleMedium,

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .focusRequester(titleFocusRequester),
            )
            CategoryChipPreview()
            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(R.string.note)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
            )
        }
    }

    if (isDatePickerVisible) {
        val datePickerState = rememberDatePickerState(
            DateTimeUtils.dateTimeToMillis(dateTime)
        )

        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerVisible = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDatePickerVisible = false
                        dateTime = dateTime.with(TemporalAdjusters.ofDateAdjuster {
                            DateTimeUtils.localDateFromMillis(
                                datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                            )
                        })
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDatePickerVisible = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    if (isTimePickerVisible) {
        val timePickerState = rememberTimePickerState(
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute
        )

        //TODO: make it a shared pref
        val showingPicker = remember {
            mutableStateOf(true)
        }
        val configuration = LocalConfiguration.current
        TimePickerDialog(
            onCancel = { isTimePickerVisible = false },
            onConfirm = {
                dateTime = dateTime.withHour(timePickerState.hour)
                    .withMinute(timePickerState.minute)
                isTimePickerVisible = false
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    IconButton(onClick = { showingPicker.value = !showingPicker.value }) {
                        val icon = if (showingPicker.value) {
                            Icons.Outlined.Keyboard
                        } else {
                            Icons.Outlined.Schedule
                        }
                        Icon(
                            icon,
                            contentDescription = if (showingPicker.value) {
                                "Switch to Text Input"
                            } else {
                                "Switch to Touch Input"
                            }
                        )
                    }
                }
            }
        ) {
            if (showingPicker.value && configuration.screenHeightDp > 400) {
                TimePicker(state = timePickerState)
            } else {
                TimeInput(state = timePickerState)
            }
        }

    }
}


@Composable
fun TimePickerDialog(
    title: String = stringResource(R.string.select_time),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = title,
                        style = MaterialTheme.typography.labelMedium
                    )
                    toggle()

                }
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(id = R.string.cancel)) }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(stringResource(id = R.string.ok)) }
                }
            }

        }
    }
}
