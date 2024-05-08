package kz.rdd.core.utils.ext

import android.icu.text.CompactDecimalFormat
import android.text.format.DateUtils
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


val decimalFormat = DecimalFormat("#.##")

fun Double.format(): String {
    return decimalFormat.format(this)
}

fun BigDecimal.format(): String {
    return decimalFormat.format(this)
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun BigDecimal.shortenNumber(locale: Locale): String {
    val compactDecimalFormat = CompactDecimalFormat.getInstance(
        locale,
        CompactDecimalFormat.CompactStyle.SHORT
    )
    return compactDecimalFormat.format(this)
}

fun Long.formatToTimer(): String {
    val duration = this.milliseconds
    return duration.toComponents { hours, minutes, seconds, _ ->
        String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds,
        )
    }
}

fun Long.withLeadingZero() = if (this in 0..9) "0$this" else this.toString()
