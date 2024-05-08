package kz.rdd.profile.presentation.fav

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.profile.domain.model.FavList
import kz.rdd.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.getViewModel

@Parcelize
class FavDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        FavScreen()
    }
}

@Composable
fun FavScreen() {
    val viewModel = getViewModel<FavViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "Favorites",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                items(state.list) {
                    Spacer(modifier = Modifier.height(5.dp))
                    BestComponent(
                        best = it,
                        onClick = { },
                        onClickBuy = viewModel::onClickBuy,
                        onClickDeleteFav = viewModel::onClickDeleteFav
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun BestComponent(
    best: FavList,
    onClick: () -> Unit,
    onClickBuy: (Int) -> Unit,
    onClickDeleteFav: (Int) -> Unit,
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
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            CommonAsyncImage(
                url = best.food.photo,
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
                    .fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = best.food.name,
                    style = LocalAppTheme.typography.l14B,
                    color = LocalAppTheme.colors.primaryText
                )

                Text(
                    text = best.food.username,
                    style = LocalAppTheme.typography.l14,
                    color = LocalAppTheme.colors.primaryText
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    repeat(best.food.grade.toDouble().toInt()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                            contentDescription = "",
                            tint = LocalAppTheme.colors.secondary
                        )
                    }
                }

            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_liked),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.error,
                    modifier = Modifier.scaledClickable { onClickDeleteFav(best.food.id) }
                )

                Spacer(modifier = Modifier.height(5.dp))

                Column(
                    modifier = Modifier
                        .background(LocalAppTheme.colors.main, RoundedCornerShape(10.dp))
                        .scaledClickable {
                            onClickBuy(best.food.id)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Buy",
                        style = LocalAppTheme.typography.l14,
                        color = LocalAppTheme.colors.white,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }
            }
        }
    }
}