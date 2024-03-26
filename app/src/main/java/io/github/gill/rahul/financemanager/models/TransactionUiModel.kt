package io.github.gill.rahul.financemanager.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import io.github.gill.rahul.financemanager.db.AmountWrapper
import java.time.LocalDateTime

data class TransactionUiModel(
    val id: Long,
    val accountId: Long,
    val type: TransactionType,
    val categoryId: Long,
    val amount: AmountWrapper,
    val title: String?,
    val description: String?,
    val dateTime: LocalDateTime,
    val dueDateTime: LocalDateTime?,
    val categoryName: String,
    val isExpense: Boolean,
    val color: Color,
    @DrawableRes
    val iconRes: Int
)