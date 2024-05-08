package kz.rdd.profile.presentation.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
class SupportDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        SupportScreen()
    }
}

@Composable
fun SupportScreen(
    controller: DestinationController = LocalDestinationController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1)
            .verticalScroll(rememberScrollState()),
    ) {
        CenteredToolbar(
            title = "Support",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = controller::navigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Support service in the \"ReadyDo\" application\n" +
                    "\n" +
                    "\n" +
                    "The support service of the Mobile AI Food Marketplace application provides fast and high-quality assistance to users, improving their interaction with the platform and helping to resolve any issues that arise.\n" +
                    "\n" +
                    "\n" +
                    "- Multilingual support\n" +
                    "- Round-the-clock availability\n" +
                    "- Various communication channels\n" +
                    "\n" +
                    "aruzhan.kabilzhan@bk.ru\n" +
                    "87072536079\n" +
                    "\n" +
                    "- Knowledge base and FAQ\n" +
                    "How do I change my password?\n" +
                    "Go to “my profile” and click the “change password” button at the bottom\n" +
                    "" +
                    "Discover quick answers to all your questions about using ReadyDo, the ultimate homemade food app, on our FAQ Support Page. From ordering to safety tips, find everything you need for a seamless experience!",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "FAQ",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.main,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How to order a meal?",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "How to order a meal?How to order a meal? How to order a meal? How to order a meal? How to order a meal? How to order a meal?",
            style = LocalAppTheme.typography.l16,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 26.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How to change the password",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "How to change the passwordo order a meal? How toHow to change the passwordder a meal? How to change the password How to change the password How to change the password",
            style = LocalAppTheme.typography.l16,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 26.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How to cancel the order?",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "How to change the passwordo order a meal? How toHow to change the passwordder a meal? How to change the password How to change the password How to change the password",
            style = LocalAppTheme.typography.l16,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 26.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What happens if you do not  received an order?",
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "How to change the passwordo order a meal? How toHow to change the passwordder a meal? How to change the password How to change the password How to change the password",
            style = LocalAppTheme.typography.l16,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 26.dp)
        )
    }
}