package io.github.gill.rahul.financemanager.models

import io.github.gill.rahul.financemanager.db.UiProperties

data class Account(
    val id: Long,
    val name: String,
    val startingBalance: Long,
    val currentBalance: Long,
    val includeInTotalBalance: Boolean,
    val uiProperties: UiProperties
)
