package wow.app.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {

    fun dateTimeToMillis(dt: LocalDateTime): Long {

        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun localDateFromMillis(millis: Long): LocalDate {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}