package wow.app.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    fun dateTimeToMillis(dt: LocalDateTime): Long {
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun localDateFromMillis(millis: Long): LocalDate {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    val yearFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy")
    val monthFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dayFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")
    val dateRangeDayFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yy")
    val timeFormat12Hours: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val simpleDateAndTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM hh:mm")
}