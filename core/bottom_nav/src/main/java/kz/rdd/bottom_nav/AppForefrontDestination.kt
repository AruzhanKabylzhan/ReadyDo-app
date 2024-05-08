package kz.rdd.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kz.rdd.core.forefront_nav.R
import kz.rdd.core.ui.DestinationController
import kz.rdd.forefront_nav.ForefrontDestination
import kz.rdd.navigate.home.HomeContentDelegate
import kz.rdd.navigate.manual.MapContentDelegate
import kz.rdd.navigate.navigate.TestContentDelegate
import kz.rdd.navigate.profile.ProfileContentDelegate
import kz.rdd.navigate.tours.ShopContentDelegate
import org.koin.compose.koinInject
@Stable
sealed interface AppForefrontDestination : ForefrontDestination {

    @Parcelize
    object Home : AppForefrontDestination {

        override val icon: Int get() = R.drawable.ic_home
        override val isEntryPoint: Boolean get() = true

        @Composable
        override fun Content(controller: DestinationController) {
            val delegate = koinInject<HomeContentDelegate>()
            delegate.Content()
        }
    }

    @Parcelize
    object Chat : AppForefrontDestination {

        override val icon: Int get() = R.drawable.ic_chats
        override val isEntryPoint: Boolean get() = false

        @Composable
        override fun Content(controller: DestinationController) {
            val delegate = koinInject<MapContentDelegate>()
            delegate.Content()
        }
    }

    @Parcelize
    object Busket : AppForefrontDestination {

        override val icon: Int get() = R.drawable.ic_basket
        override val isEntryPoint: Boolean get() = false

        @Composable
        override fun Content(controller: DestinationController) {
            val delegate = koinInject<TestContentDelegate>()
            delegate.Content()
        }
    }

    @Parcelize
    object Catalog : AppForefrontDestination {

        override val icon: Int get() = R.drawable.ic_catalog_30
        override val isEntryPoint: Boolean get() = false

        @Composable
        override fun Content(controller: DestinationController) {
            val settingsDelegate = koinInject<ShopContentDelegate>()
            settingsDelegate.Content()
        }
    }

    @Parcelize
    object Profile : AppForefrontDestination {

        override val icon: Int get() = R.drawable.ic_profile
        override val isEntryPoint: Boolean get() = false

        @Composable
        override fun Content(controller: DestinationController) {
            val settingsDelegate = koinInject<ProfileContentDelegate>()
            settingsDelegate.Content()
        }
    }

}