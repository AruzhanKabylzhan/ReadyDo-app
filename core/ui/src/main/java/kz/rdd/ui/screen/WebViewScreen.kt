package kz.rdd.core.ui.screen

import android.view.View
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import dev.olshevski.navigation.reimagined.NavAction
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.navigation.NavigationAnimation
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.ui.utils.get
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.LoadingProgress

@Composable
fun WebViewScreen(
    title: String,
    url: String,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenteredToolbar(
            title = title,
            isNavigationIconVisible = true,
            onNavigationIconClick = onBackPressed,
        )
        val webViewState = rememberWebViewState(url = url)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .safeNavigationPadding(),
        ) {
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize(),
                onCreated = {
                    it.overScrollMode = View.OVER_SCROLL_NEVER
                    it.isVerticalScrollBarEnabled = false
                    it.isScrollbarFadingEnabled = true
                    it.settings.javaScriptEnabled = true
                    it.settings.javaScriptCanOpenWindowsAutomatically = true
                    it.settings.domStorageEnabled = true
                }
            )
            LoadingProgress(
                modifier = Modifier.align(Alignment.Center),
                isVisible = webViewState.isLoading
            )
        }
    }
}

@Parcelize
data class WebViewScreenDestination(
    val title: VmRes.Parcelable<CharSequence>,
    val url: String,
) : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        WebViewScreen(
            title = title.get().toString(),
            url = url,
            onBackPressed = controller::navigateBack
        )
    }

    override fun openTransition(action: NavAction, from: Destination?): ContentTransform {
        return NavigationAnimation.slideHorizontalFromEnd()
    }

    override fun closeTransition(to: Destination?): ContentTransform {
        return NavigationAnimation.slideHorizontalFromStart()
    }
}