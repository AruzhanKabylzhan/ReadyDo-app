package kz.rdd.busket.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.navigate.navigate.TestContentDelegate
import org.koin.androidx.compose.getViewModel

object BusketContentDelegateImpl : TestContentDelegate {
    @Composable
    override fun Content() {
        BusketScreen()
    }
}

@Composable
fun BusketScreen() {
    val viewModel = getViewModel<BusketViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    LaunchedEffect(Unit) {
        viewModel.load()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "Backet",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = false,
        )

        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    items(state.products) {
                        Spacer(modifier = Modifier.height(5.dp))
                        BestComponent(
                            best = it,
                            onClick = { },
                            onClickDelete = viewModel::onClickDelete
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        LocalAppTheme.colors.main,
                        RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
                    )
                    .background(LocalAppTheme.colors.white)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.total.toString().plus("$"),
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonButton(
                    text = "Pay",
                    onClick = viewModel::onClickPay
                )
            }
        }


    }
}

@Composable
fun BestComponent(
    best: BusketProducts,
    onClick: () -> Unit,
    onClickDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .background(LocalAppTheme.colors.main, RoundedCornerShape(12.dp))
            .scaledClickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        contentColor = LocalAppTheme.colors.main
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalAppTheme.colors.white, RoundedCornerShape(15.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CommonAsyncImage(
                url = best.productInfo.photo,
                modifier = Modifier
                    .size(50.dp)
                    .border(
                        width = 1.dp,
                        color = LocalAppTheme.colors.stroke,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = best.productInfo.name.plus(" | x").plus(best.quantity),
                    style = LocalAppTheme.typography.l14B,
                    color = LocalAppTheme.colors.primaryText
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_users_24),
                        contentDescription = "",
                        tint = LocalAppTheme.colors.gray2
                    )

                    Text(
                        text = best.productInfo.username,
                        style = LocalAppTheme.typography.l14,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = best.productInfo.price.toString().plus("$"),
                    style = LocalAppTheme.typography.l14,
                    color = LocalAppTheme.colors.primaryText
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete_outline_24),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.main,
                    modifier = Modifier
                        .scaledClickable {
                            onClickDelete(best.productInfo.id)
                        }
                )
            }
        }
    }
}