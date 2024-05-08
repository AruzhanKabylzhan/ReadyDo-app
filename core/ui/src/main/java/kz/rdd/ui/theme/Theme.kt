package kz.rdd.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import kz.rdd.core.ui.LocalDestinationControllerProvider
import kz.rdd.core.ui.MockDestinationController
import kz.rdd.core.utils.ext.defaultLanguage
import java.util.Locale

@Composable
fun AppTheme(
    currentLocale: Locale = Locale.forLanguageTag("ru"),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorProvider provides lightColors,
        LocalTypographyProvider provides typography,
        LocalLocaleProvider provides currentLocale,
        content = content
    )
}

object LocalAppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current

    val typography: Typographies
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyProvider.current
}

val LocalColorProvider = staticCompositionLocalOf<Colors> {
    error("No default colors provided")
}

val LocalTypographyProvider = staticCompositionLocalOf<Typographies> {
    error("No default typography provided")
}

val LocalLocaleProvider = staticCompositionLocalOf<Locale> {
    defaultLanguage
}

@Composable
fun PreviewAppTheme(
    locale: Locale = defaultLanguage,
    content: @Composable () -> Unit
) {
    AppTheme(
        currentLocale = locale
    ) {
        CompositionLocalProvider(
            LocalDestinationControllerProvider provides MockDestinationController()
        ) {
            content()
        }
    }
}