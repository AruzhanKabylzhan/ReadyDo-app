package kz.rdd.core.ui.utils

import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.util.fastAll

suspend fun AwaitPointerEventScope.waitForUpRegionSafe(
    pass: PointerEventPass = PointerEventPass.Main
): PointerInputChange {
    while (true) {
        val event = awaitPointerEvent(pass)
        if (event.changes.fastAll { it.changedToUp() }) {
            // All pointers are up
            return event.changes[0]
        }
    }
}

suspend fun AwaitPointerEventScope.dragUntilConsumed(
    change: PointerInputChange,
    onDrag: (PointerInputChange) -> Unit
): Boolean {
    var lastChange = change
    while (true) {
        val currentChange = awaitDragOrCancellation(lastChange.id) ?: return false

        if (currentChange.changedToUpIgnoreConsumed()) {
            return true
        }

        onDrag(currentChange)

        if (currentChange.isConsumed) {
            return true
        }

        lastChange = currentChange
    }
}
