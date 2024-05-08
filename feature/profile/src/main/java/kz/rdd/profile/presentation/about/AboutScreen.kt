package kz.rdd.profile.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar

@Parcelize
class AboutDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        AboutScreen()
    }
}

@Composable
fun AboutScreen(
    controller: DestinationController = LocalDestinationController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "About",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = controller::navigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About Application",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.main,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About application \"ReadyDo\" \n" +
                    "The \"ReadyDo\" application is designed to simplify access to homemade food and support local chefs. This platform allows users to easily find and buy homemade food, and also offers chefs a unique opportunity to sell their dishes directly to consumers.\n" +
                    "\n" +
                    "- Personalization through AI: The application uses artificial intelligence algorithms to offer personalized recommendations and search for dishes that match the taste preferences and dietary requirements of users.\n" +
                    "- Support for local producers: The platform helps expand the customer base of local chefs by providing them with the opportunity to sell without intermediaries.\n" +
                    "- Sustainable Consumption: The app encourages the selection of local and seasonal products, contributing to reducing the carbon footprint and supporting sustainable food practices.\n" +
                    "- Social Features: In-app communication opportunities, including reviews, ratings, and forums, help strengthen the community and share experiences between users.\n" +
                    "- Intuitive interface: The modern interface design makes the application easy to use, making it accessible to a wide range of users.",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}