package kz.rdd.core.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class LocalDateTypeAdapter : TypeAdapter<LocalDate>() {

    override fun write(out: JsonWriter, value: LocalDate) {
        out.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
    }

    override fun read(input: JsonReader): LocalDate? {
        val text = input.nextString()
        if (text.isEmpty()) return null

        return tryParse {
            LocalDate.parse(text)
        }
    }
}

class LocalTimeTypeAdapter : TypeAdapter<LocalTime>() {

    override fun write(out: JsonWriter, value: LocalTime) {
        out.value(DateTimeFormatter.ISO_LOCAL_TIME.format(value))
    }

    override fun read(input: JsonReader): LocalTime? {
        val text = input.nextString()
        if (text.isEmpty()) return null

        return tryParse {
            LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME)
        }
    }
}

class OffsetDateTimeTypeAdapter : TypeAdapter<OffsetDateTime>() {

    override fun write(out: JsonWriter, value: OffsetDateTime) {
        out.value(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value))
    }

    override fun read(input: JsonReader): OffsetDateTime? {
        val text = input.nextString()
        if (text.isEmpty()) return null

        return tryParse {
            OffsetDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } ?: tryParse {
            OffsetDateTime.parse(
                text.substring(0, text.length - 2) + ":" + text.substring(
                    text.length - 2,
                    text.length
                ),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
            )
        }
    }
}

class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    private val dateFormatterVariant =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(dateFormatter.format(value))
    }

    override fun read(input: JsonReader): LocalDateTime? {
        val text = input.nextString()
        return text.parseOrNull(dateFormatter)
            ?: text.parseOrNull(dateFormatterVariant)
            ?: text.parseOrNull(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    private fun String.parseOrNull(formatter: DateTimeFormatter): LocalDateTime? {
        return try {
            LocalDateTime.parse(
                this,
                formatter
            )
        } catch (e: Exception) {
            null
        }
    }
}

private fun <T> tryParse(parse: () -> T): T? {
    return try {
        parse()
    } catch (e: Exception) {
        Timber.d(e)
        null
    }
}
