package kz.rdd.core.ui.widgets

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.rdd.core.ui.BuildConfig
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.utils.AppConfig
import kz.rdd.core.utils.ServerConfiguration
import kz.rdd.store.UserStore
import org.koin.androidx.compose.getViewModel

@Composable
fun ServerConfigurationSwitcher(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    if (BuildConfig.DEBUG) {
        val viewModel = getViewModel<ServerConfigurationSwitcherViewModel>()
        val state = viewModel.uiState.collectAsStateWithLifecycle()
        SettingToggleItem(
            text = "Current Server Configuration",
            description = state.value.currentEnvironment.name,
            icon = R.drawable.ic_about_app,
            checked = false,
            onCheckedChange = {
            },
            modifier = modifier,
        )
    }
}

internal data class State(
    val currentEnvironment: ServerConfiguration = ServerConfiguration.PROD,
) : UiState

internal class ServerConfigurationSwitcherViewModel(
    private val appConfig: AppConfig,
    private val userStore: UserStore,
) : BaseViewModel<State>() {
    override fun createInitialState() = State()
}

@Composable
internal fun SettingToggleItem(
    text: String,
    description: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(LocalAppTheme.colors.white)
            .clickableWithIndication(
                rippleColor = LocalAppTheme.colors.primaryText,
                onClick = {
                    onCheckedChange(!checked)
                },
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = text,
                style = LocalAppTheme.typography.l14B,
                color = LocalAppTheme.colors.primaryText,
                textAlign = TextAlign.Start,
            )
            Text(
                text = description,
                style = LocalAppTheme.typography.l12,
                color = LocalAppTheme.colors.accentText,
                textAlign = TextAlign.Start,
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalAppTheme.colors.white,
                checkedTrackColor = LocalAppTheme.colors.main,
            )
        )
    }
}
