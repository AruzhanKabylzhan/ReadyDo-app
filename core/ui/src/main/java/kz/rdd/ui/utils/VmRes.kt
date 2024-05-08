package kz.rdd.core.ui.utils

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Interface for get context depending value.
 *
 * Ex: If you use the application context for getColor and after the user changes the color, you may have problems with the color not changing,
 * to fix this you need to use this interface with the inherited class and get the value from the activity, fragment or view.
 */
@Stable
sealed interface VmRes<T> {

    /**
     * Get a value from inherited class.
     *
     * @param context pass the context from the fragment, activity or view to get the value depending on the configuration change.
     * @return value
     */
    fun get(context: Context): T

    /**
     * Returns a string representation of the object.
     *
     * @param context pass the context from the fragment, activity or view to get the value depending on the configuration change.
     * @return string value. Ex: if need convert CharSequence to String instead use .get(context).toString() you can .toString(context) or implement self logic for inherited class
     */
    fun toString(context: Context): String {
        return get(context).toString()
    }

    interface Parcelable<T> : VmRes<T>, android.os.Parcelable

    @Parcelize
    class StrRes(
        @StringRes private val value: Int,
        private vararg val args: @RawValue Any? = arrayOf(),
    ) : Parcelable<CharSequence> {
        override fun get(context: Context): CharSequence {
            return if (args.isNotEmpty()) {
                context.getString(value,
                    *args.map { if (it is VmRes<*>) it.get(context) else it }.toTypedArray())
            } else {
                context.getString(value)
            }
        }
    }

    @Parcelize
    class StrQuantityRes(
        @PluralsRes private val value: Int,
        private val quantity: Int,
        private vararg val args: @RawValue Any? = arrayOf(),
    ) : Parcelable<CharSequence> {
        override fun get(context: Context): CharSequence {
            return if (args.isNotEmpty()) {
                context.resources.getQuantityString(
                    value,
                    quantity,
                    *args.map { if (it is VmRes<*>) it.get(context) else it }.toTypedArray(),
                )
            } else {
                context.resources.getQuantityString(value, quantity, quantity)
            }
        }
    }

    @Parcelize
    class StrArrayRes(
        @ArrayRes private val value: Int,
    ) : Parcelable<Array<String>> {
        override fun get(context: Context): Array<String> {
            return context.resources.getStringArray(value)
        }
    }

    @Parcelize
    class Str(
        private val value: CharSequence,
        private vararg val args: @RawValue Any? = arrayOf(),
    ) : Parcelable<CharSequence> {
        override fun get(context: Context): CharSequence {
            return if (args.isNotEmpty()) {
                value.toString().format(*args.map { if (it is VmRes<*>) it.get(context) else it }
                    .toTypedArray())
            } else {
                value
            }
        }
    }

    @Parcelize
    class ColorRes(@androidx.annotation.ColorRes private val value: Int) : Parcelable<Int> {
        override fun get(context: Context): Int {
            return ContextCompat.getColor(context, value)
        }
    }

    @Parcelize
    class DrawableRes(@androidx.annotation.DrawableRes private val value: Int) :
        Parcelable<Drawable?> {
        override fun get(context: Context): Drawable? {
            return ContextCompat.getDrawable(context, value)
        }
    }

    class Callback<R>(val block: (Context) -> R) : VmRes<R> {
        override fun get(context: Context): R {
            return block.invoke(context)
        }
    }

    @Parcelize
    class Span(private vararg val values: Settings) : Parcelable<CharSequence> {

        @Parcelize
        class Settings(
            vararg val text: Parcelable<CharSequence>,
            val color: ColorRes? = null,
            val isBold: Boolean = false,
        ) : android.os.Parcelable

        override fun get(context: Context): CharSequence {
            return buildSpannedString {
                values.forEach { settings ->
                    val spans = mutableListOf<Any>().apply {
                        if (settings.color != null) {
                            add(ForegroundColorSpan(settings.color.get(context)))
                        }
                        if (settings.isBold) {
                            add(StyleSpan(Typeface.BOLD))
                        }
                    }
                    inSpans(*spans.toTypedArray()) {
                        settings.text.forEach {
                            append(it.get(context))
                        }
                    }
                }
            }
        }
    }

    @Parcelize
    class Operation(
        private val first: Parcelable<CharSequence>,
        private val second: Parcelable<CharSequence>,
        private val type: Type,
    ) : Parcelable<CharSequence> {

        enum class Type {
            SUBSTRING_BEFORE_LAST,
            SUBSTRING_AFTER
        }

        override fun get(context: Context): CharSequence {
            return when (type) {
                Type.SUBSTRING_BEFORE_LAST -> first.toString(context)
                    .substringBeforeLast(second.toString(context))
                Type.SUBSTRING_AFTER -> first.toString(context)
                    .substringAfter(second.toString(context))
            }
        }
    }
}

fun CharSequence.toVmResStr(): VmRes.Parcelable<CharSequence> {
    return VmRes.Str(this)
}

fun Int.toVmResStr(): VmRes.Parcelable<CharSequence> {
    return VmRes.StrRes(this)
}

operator fun VmRes.Parcelable<CharSequence>.plus(s: VmRes.Parcelable<CharSequence>) =
    VmRes.Span(VmRes.Span.Settings(this, s))

fun VmRes.Parcelable<CharSequence>.substringBeforeLast(s: VmRes.Parcelable<CharSequence>) =
    VmRes.Operation(this, s, VmRes.Operation.Type.SUBSTRING_BEFORE_LAST)

fun VmRes.Parcelable<CharSequence>.substringAfter(s: VmRes.Parcelable<CharSequence>) =
    VmRes.Operation(this, s, VmRes.Operation.Type.SUBSTRING_AFTER)

@Composable
fun <T> VmRes<T>.get(): T {
    return get(LocalContext.current)
}

@Composable
fun <T> VmRes<T>.toStringCompose(): String {
    return toString(LocalContext.current)
}
