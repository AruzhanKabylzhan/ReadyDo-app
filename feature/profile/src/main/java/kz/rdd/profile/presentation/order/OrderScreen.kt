package kz.rdd.profile.presentation.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.busket.domain.model.BusketList
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.R
import kz.rdd.profile.presentation.fav.FavViewModel
import org.koin.androidx.compose.getViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Parcelize
class OrderDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        OrderScreen()
    }
}

@Composable
fun OrderScreen() {
    val viewModel = getViewModel<OrderViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "Order history",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn {
                items(state.list) {
                    Spacer(modifier = Modifier.height(5.dp))
                    OrderComponent(busket = it)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun OrderComponent(
    busket: BusketList,
) {
    val date = busket.dateOrdered.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    Card(
        modifier = Modifier
            .background(LocalAppTheme.colors.main, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        contentColor = LocalAppTheme.colors.main
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Order#".plus(busket.id),
                style = LocalAppTheme.typography.l15,
                color = LocalAppTheme.colors.primaryText
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.date),
                    contentDescription = "",
                )

                Text(
                    text = "Data ordered: ".plus(date),
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.address),
                    contentDescription = "",
                )

                Text(
                    text = "Address: ".plus(busket.customer.address),
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }


            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bucket),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.checkGreen
                )

                Text(
                    text = "Status: ".plus("Shopped"),
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Total price: ".plus(busket.totalPrice).plus("$"),
                style = LocalAppTheme.typography.l22,
                color = LocalAppTheme.colors.main
            )

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}