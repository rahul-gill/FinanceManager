package io.github.gill.rahul.financemanager.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.gill.rahul.financemanager.ui.components.DateRangeType
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    var dateRangeType: DateRangeType by mutableStateOf(DateRangeType.Daily)
    var dateRangeStartDate by mutableStateOf(LocalDate.now())
    var showDateRangeTypeDialog by mutableStateOf(false)
    var customRangeLengthDays by mutableLongStateOf(10L)

}