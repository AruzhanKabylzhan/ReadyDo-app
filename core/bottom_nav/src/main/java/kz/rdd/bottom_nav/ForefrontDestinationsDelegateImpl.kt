package kz.rdd.navigate

import kz.rdd.forefront_nav.ForefrontDestination
import kz.rdd.forefront_nav.ForefrontDestinationsDelegate

internal class ForefrontDestinationsDelegateImpl : ForefrontDestinationsDelegate {
    override fun getDestinations(): List<ForefrontDestination> = listOf(
        AppForefrontDestination.Catalog,
        AppForefrontDestination.Chat,
        AppForefrontDestination.Home,
        AppForefrontDestination.Busket,
        AppForefrontDestination.Profile,
    )
}