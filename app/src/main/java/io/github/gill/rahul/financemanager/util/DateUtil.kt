package io.github.gill.rahul.financemanager.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun millisToLocalDate(millis: Long): LocalDate {
    return Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}