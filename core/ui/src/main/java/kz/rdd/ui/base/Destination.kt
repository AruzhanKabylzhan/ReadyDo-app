package kz.rdd.core.ui.base

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.material.BottomSheetProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.navigation.NavigationAnimation
import kz.rdd.core.ui.widgets.animated.RotatedReveal

interface Destination : Screen {
    val isMain: Boolean get() = false
    val isLogin: Boolean get() = false

    @Composable
    fun Content(controller: DestinationController)

    fun openTransition(action: NavAction, from: Destination?): ContentTransform =
        NavigationAnimation.crossfade()

    fun closeTransition(to: Destination?): ContentTransform = NavigationAnimation.crossfade()
}

interface SheetDestination : Screen {
    val sheetSpec: BottomSheetProperties
        get() = BottomSheetProperties(skipHalfExpanded = true)

    val dismissByTouch get() = true

    val sendDefaultDismissedResult: Boolean get() = true

    fun onDismiss(destinationController: DestinationController) = Unit

    @Composable
    fun Content(controller: DestinationController)

    companion object {
        const val ACCEPT_RESULT_DISMISSED = "sheet_destination_dismissed"
    }
}

interface DialogDestination : Screen {

    val dismissByTouch get() = true

    val blurRadius: Dp get() = 3.dp

    @Composable
    fun Content(controller: DestinationController)

    @Composable
    fun OpenAnimation(controller: DestinationController) {
        RotatedReveal {
            Content(controller = controller)
        }
    }

    fun onDismiss(
        destinationController: DestinationController,
        coroutineScope: CoroutineScope
    ) = Unit
}

@Stable
interface AcceptResult {

    val result: Flow<Data>

    suspend fun setResult(data: Data)

    data class Data(
        val key: String? = null,
        val additionalKey: String? = null,
        val value: Any?,
    )

    @Stable
    class Base : AcceptResult {
        private val _result: Channel<Data> = Channel(Channel.BUFFERED)
        override val result = _result.receiveAsFlow().shareIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            replay = 0,
        )

        override suspend fun setResult(data: Data) {
            _result.send(data)
        }
    }
}
