package kz.rdd.core.ui.screen.loading_indicator

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.base.viewmodel.UiEvent
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class LoadingIndicatorDialogDestination(
    override val dismissByTouch: Boolean = true,
    private val behavior: Behavior,
) : DialogDestination {

    interface Behavior : Parcelable {
        suspend fun load(
            setProgress: (Float) -> Unit,
            onSuccessEvent: (UiEvent) -> Unit,
        )

        fun onError(
            setEvent: (UiEvent) -> Unit,
        ) = Unit

        fun throwError(): Nothing = throw Exception()
    }

    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<LoadingIndicatorViewModel> {
            parametersOf(behavior)
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val progress = state.progress
        ComposeEffectHandler(effect = viewModel.effect)
        ComposeEventHandler(event = viewModel.event)
        Card(
            modifier = Modifier.padding(horizontal = 24.dp),
            backgroundColor = LocalAppTheme.colors.bg1,
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp),
                    progress = progress,
                    color = LocalAppTheme.colors.primaryText,
                    strokeWidth = 2.dp,
                    trackColor = LocalAppTheme.colors.stroke2,
                    strokeCap = StrokeCap.Round,
                )
                Text(
                    text = stringResource(id = R.string.loading_indicator_please_wait),
                    color = LocalAppTheme.colors.primaryText,
                    style = LocalAppTheme.typography.l16,
                )
            }
        }
    }
}
