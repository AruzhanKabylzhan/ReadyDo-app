package kz.rdd.forefront_nav

import androidx.compose.runtime.Stable
import kz.rdd.core.ui.base.Destination

@Stable
interface ForefrontDestination : Destination {
    val icon: Int

    val isEntryPoint: Boolean

}

@Stable
interface ForefrontDestinationsDelegate {
    fun getDestinations(): List<ForefrontDestination>
}
