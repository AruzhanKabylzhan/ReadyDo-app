package kz.rdd.core.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kz.rdd.core.ui.R

private val fontFamily = FontFamily(
    Font(resId = R.font.samsung_sans_thin, weight = FontWeight.Thin),
    Font(resId = R.font.samsung_sans_light, weight = FontWeight.Light),
    Font(resId = R.font.samsung_sans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.samsung_sans_medium, weight = FontWeight.Medium),
    Font(resId = R.font.samsung_sans_bold, weight = FontWeight.Bold),
)

data class Typographies(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val l24: TextStyle,
    val l22: TextStyle,
    val l20: TextStyle,
    val l10: TextStyle,
    val l11: TextStyle,
    val l12: TextStyle,
    val l12B: TextStyle,
    val l12M: TextStyle,
    val l12C: TextStyle,
    val l13: TextStyle,
    val l13B: TextStyle,
    val l14: TextStyle,
    val l14M: TextStyle,
    val l14B: TextStyle,
    val l14E: TextStyle,
    val l15: TextStyle,
    val l16: TextStyle,
    val l16B: TextStyle,
    val l17: TextStyle,
    val l17B: TextStyle,
    val l18: TextStyle,
    val l18B: TextStyle,
)

val typography = Typographies(
    h1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
        lineHeight = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        lineHeight = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    l24 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    l10 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        lineHeight = 13.sp
    ),
    l11 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 11.sp,
        lineHeight = 14.sp
    ),
    l12 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    l12B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.4.sp
    ),
    l12M = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.4.sp
    ),
    l12C = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 14.4.sp
    ),
    l13 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        lineHeight = 16.sp
    ),
    l13B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 13.sp,
        lineHeight = 16.sp
    ),
    l14 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    l14M = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    l14B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    l14E = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    l15 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        lineHeight = 19.sp
    ),
    l16 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 19.sp
    ),
    l16B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 19.sp
    ),
    l17 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 17.sp,
        lineHeight = 20.sp
    ),
    l17B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 20.sp
    ),
    l18 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 23.sp
    ),
    l18B = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        lineHeight = 18.sp
    ),
    l20 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 29.sp
    ),
    l22 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
    ),
)
