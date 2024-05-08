package kz.rdd.core.utils.ext

import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

const val DATE_PATTERN = "dd.MM.yyyy"
const val DATE_TIME_PATTERN = "HH:mm, dd.MM.yyyy"

fun LocalDate.asEpochMilliWithZoneId(
    zoneOffset: ZoneOffset = OffsetDateTime.now().offset
) = atTime(LocalTime.MIN).toInstant(zoneOffset).toEpochMilli()

fun Long.epochToLocalDate() = LocalDate.ofEpochDay(Duration.ofMillis(this).toDays())

fun LocalDate.isFinishedDate(): Boolean {
    return asEpochMilliWithZoneId() <= System.currentTimeMillis()
}

fun LocalDate.remainingTimeString(
    includeSeconds: Boolean = false,
    nowTimeMillis: Long = System.currentTimeMillis()
): String? {
    val timeInMilliSeconds = asEpochMilliWithZoneId() - nowTimeMillis
    if (timeInMilliSeconds <= 0) return null
    return timeInMilliSeconds.remainingTimeString(includeSeconds)
}

fun Long.remainingTimeString(
    includeSeconds: Boolean = false,
): String {
    val seconds: Long = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    var time = days.toInt().withLeadingZeroIfNeed() + ":" + (hours % 24).toInt()
        .withLeadingZeroIfNeed() + ":" + (minutes % 60).toInt().withLeadingZeroIfNeed()
    if (includeSeconds) {
        time += ":" + (seconds % 60).toInt().withLeadingZeroIfNeed()
    }
    return time
}

fun Long.toReadableSeconds(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%d:%02d", minutes, seconds)
}

fun LocalDate.remainingTime(
    nowTimeMillis: Long = System.currentTimeMillis()
): Triple<Int, Int, Int> {
    val timeInMilliSeconds = asEpochMilliWithZoneId() - nowTimeMillis
    if (timeInMilliSeconds <= 0) return Triple(0, 0, 0)
    val seconds: Long = timeInMilliSeconds / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return Triple(days.toInt(), (hours % 24).toInt(), (minutes % 60).toInt())
}

fun LocalDate.asDateText(
    withYear: Boolean = false,
) = "${dayOfMonth.withLeadingZeroIfNeed()}.${monthValue.withLeadingZeroIfNeed()}".run {
    if (withYear) this + ".${year.toString().takeLast(2)}" else this
}

fun LocalDate.atStartWeek() = with(TemporalAdjusters.previousOrSame(firstDayOfWeekFromLocale()))
fun LocalDate.atEndWeek() = with(TemporalAdjusters.nextOrSame(lastDayOfWeekFromLocale()))

fun LocalDate.isToday() = LocalDate.now() == this

fun LocalDate.isTomorrow() = LocalDate.now().plusDays(1) == this

fun LocalDate.isYesterday() = LocalDate.now().minusDays(1) == this

fun LocalDate.isInCurrentWeek(
    fromNow: Boolean = false
) = this in (if (fromNow) LocalDate.now() else atStartWeek())..(LocalDate.now().atEndWeek())

fun LocalDate.isInNextRange(
    range: Int = 7
) = this in LocalDate.now()..LocalDate.now().plusDays(range.toLong())

fun firstDayOfWeekFromLocale(): DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
fun lastDayOfWeekFromLocale(): DayOfWeek = firstDayOfWeekFromLocale().plus(6)

fun Int.withLeadingZeroIfNeed() = if (this in (0..9)) "0$this" else this.toString()


class DateIterator(
    private val startDate: LocalDate,
    private val endDateInclusive: LocalDate,
    private val stepDays: Long
) : Iterator<LocalDate> {
    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDateInclusive

    override fun next(): LocalDate {

        val next = currentDate

        currentDate = currentDate.plusDays(stepDays)

        return next

    }

}

class DateProgression(
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    val stepDays: Long = 1
) : Iterable<LocalDate>, ClosedRange<LocalDate> {

    override fun iterator(): Iterator<LocalDate> = DateIterator(start, endInclusive, stepDays)
    infix fun step(days: Long) = DateProgression(start, endInclusive, days)
}

operator fun LocalDate.rangeTo(other: LocalDate) = DateProgression(this, other)
