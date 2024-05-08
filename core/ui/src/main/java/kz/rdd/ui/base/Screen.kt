package kz.rdd.core.ui.base

import android.os.Parcelable
import androidx.compose.runtime.Stable

@Stable
sealed interface Screen : Parcelable {
    fun onNavigateBack(): Boolean = true

    val tag: String? get() = null
}
