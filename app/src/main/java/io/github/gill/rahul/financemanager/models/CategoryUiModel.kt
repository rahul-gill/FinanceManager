package io.github.gill.rahul.financemanager.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color


data class CategoryUiModel(
    val id: Long,
    val name: String,
    val isExpenseCategory: Boolean,
    val color: Color,
    @DrawableRes
    val iconRes: Int,
    val orderNum: Long
)