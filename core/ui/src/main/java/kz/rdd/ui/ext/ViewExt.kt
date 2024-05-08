package kz.rdd.core.ui.ext

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kz.rdd.core.ui.R

fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    val keyboardPercent = 0.15
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * keyboardPercent
}

val Float.dpToPx: Float get() = this * Resources.getSystem().displayMetrics.density
val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.spToPx: Float get() = this * Resources.getSystem().displayMetrics.scaledDensity


enum class TooltipArrowOrientation {
    TOP, BOTTOM, START, END,
}

fun View.showTooltip(
    text: String,
    lifecycleOwner: LifecycleOwner?,
    @Px xOffset: Int = 0,
    @Px yOffset: Int = 0,
    autoDismissDuration: Long = 2000L,
    focusable: Boolean = true,
    arrowPosition: Float = 0.5F,
    backgroundColorArgb: Int = Color.BLACK,
    arrowOrientation: TooltipArrowOrientation = TooltipArrowOrientation.TOP,
    textSize: Float = 14f,
    arrowSize: Int = 16,
    paddingVertical: Int = 4,
    paddingHorizontal: Int = 8,
    marginHorizontal: Int = 8,
    marginVertical: Int = 0,
    dismissListener: (() -> Unit)? = null
) {
    val tooltip = Balloon.Builder(context)
        .setHeight(BalloonSizeSpec.WRAP)
        .setText(text)
        .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        .setTextColorResource(android.R.color.white)
        .setTextSize(textSize)
        .setArrowSize(arrowSize)
        .setPaddingHorizontal(paddingHorizontal)
        .setPaddingVertical(paddingVertical)
        .setCornerRadius(8f)
        .setArrowOrientation(
            when(arrowOrientation) {
                TooltipArrowOrientation.TOP -> ArrowOrientation.TOP
                TooltipArrowOrientation.BOTTOM -> ArrowOrientation.BOTTOM
                TooltipArrowOrientation.START -> ArrowOrientation.START
                TooltipArrowOrientation.END -> ArrowOrientation.END
            }
        )
        .setArrowDrawableResource(R.drawable.ic_tooltip_arrow)
        .setBackgroundColor(backgroundColorArgb)
        .setBalloonAnimation(BalloonAnimation.FADE)
        .setLifecycleOwner(lifecycleOwner)
        .setFocusable(focusable)
        .setMarginVertical(marginVertical)
        .setMarginHorizontal(marginHorizontal)
        .setAutoDismissDuration(autoDismissDuration)
        .setArrowPosition(arrowPosition)
        .apply {
            val actualTypeface = ResourcesCompat.getFont(context, R.font.samsung_sans_medium)
            if (actualTypeface != null) {
                setTextTypeface(actualTypeface)
            }
            setOnBalloonDismissListener {
                dismissListener?.invoke()
            }
        }
        .build()

    tooltip.showAlignTop(anchor = this, xOff = xOffset, yOff = yOffset)
}