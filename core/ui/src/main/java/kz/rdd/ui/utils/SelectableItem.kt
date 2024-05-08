package kz.rdd.core.ui.utils

import androidx.compose.runtime.Stable

@Stable
open class SelectableItem(
    val id: Int,
    val name: VmRes<CharSequence>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectableItem

        if (name != other.name) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id
        return result
    }
}
