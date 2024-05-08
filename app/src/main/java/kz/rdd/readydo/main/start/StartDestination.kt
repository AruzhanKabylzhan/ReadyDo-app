package kz.rdd.main.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.AcceptResult
import kz.rdd.core.ui.base.Destination
import kz.rdd.forefront_nav.ForefrontDestinationsDelegate
import kz.rdd.login.presentation.LoginScreen
import kz.rdd.main.LoadingUserDetailsScreen
import kz.rdd.main.MainNavigationScreen
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent

@Parcelize
internal sealed interface StartDestination : Destination {

    @Immutable
    @Parcelize
    object Login : StartDestination{
        override val isLogin: Boolean
            get() = true

        @Composable
        override fun Content(controller: DestinationController) {
            LoginScreen(controller)
        }
    }

    @Immutable
    @Parcelize
    class Main: StartDestination,
        AcceptResult by AcceptResult.Base(),
        KoinComponent {

        override val isMain: Boolean
            get() = true

        @Composable
        override fun Content(controller: DestinationController) {
            val delegate = koinInject<ForefrontDestinationsDelegate>()
            MainNavigationScreen(
                forefrontDestinationsDelegate = delegate,
            )
        }
    }

    @Immutable
    @Parcelize
    object LoadingUserDetails : StartDestination {

        @Composable
        override fun Content(controller: DestinationController) {
            LoadingUserDetailsScreen(controller)
        }
    }
}