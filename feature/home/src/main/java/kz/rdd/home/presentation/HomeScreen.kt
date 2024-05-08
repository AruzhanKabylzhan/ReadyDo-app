package kz.rdd.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.catalog.domain.Food
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.navigate.home.HomeContentDelegate
import org.koin.androidx.compose.getViewModel

object HomeContentDelegateImpl : HomeContentDelegate {
    @Composable
    override fun Content() {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {

    val viewModel = getViewModel<HomeViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenteredToolbar(
            title = "Home",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = false,
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .weight(0.5f)
                    .background(LocalAppTheme.colors.main, RoundedCornerShape(12.dp))
                    .scaledClickable { viewModel.onClickBest(true) },
                shape = RoundedCornerShape(12.dp),
                elevation = 10.dp,
                contentColor = LocalAppTheme.colors.main
            ) {
                Column(
                    modifier = Modifier
                        .background(LocalAppTheme.colors.white, RoundedCornerShape(15.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "The Best Chefs",
                        style = LocalAppTheme.typography.l14B,
                        color = LocalAppTheme.colors.primaryText,
                        modifier = Modifier.padding(10.dp)
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_chef),
                        contentDescription = "",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))


            Card(
                modifier = Modifier
                    .weight(0.5f)
                    .background(LocalAppTheme.colors.main, RoundedCornerShape(12.dp))
                    .scaledClickable { viewModel.onClickBest(false) },
                shape = RoundedCornerShape(12.dp),
                elevation = 10.dp,
                contentColor = LocalAppTheme.colors.main
            ) {
                Column(
                    modifier = Modifier
                        .background(LocalAppTheme.colors.white, RoundedCornerShape(15.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "The Best Meals",
                        style = LocalAppTheme.typography.l14B,
                        color = LocalAppTheme.colors.primaryText,
                        modifier = Modifier.padding(10.dp)
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_meals),
                        contentDescription = "",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        Text(
            text = "Recommendation",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .padding(top = 21.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(state.value.meals) {
                    MealItem(
                        food = it,
                        title = it.name,
                        imageUrl = it.photo,
                        price = it.price.toString().plus("$"),
                        onClick = viewModel::onCLickMeal
                    )
                }
            }
        }
    }
}

@Composable
fun MealItem(
    food: Food,
    title: String,
    imageUrl: String,
    price: String,
    onClick: (Food) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(220.dp, 220.dp)
            .padding(5.dp)
            .scaledClickable { onClick(food) },
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp, min = 250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    CommonAsyncImage(
                        url = imageUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LocalAppTheme.colors.white, RoundedCornerShape(6.dp))
                            .padding(start = 10.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = title,
                            style = LocalAppTheme.typography.l18B,
                            color = LocalAppTheme.colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Text(
                            text = price,
                            style = LocalAppTheme.typography.l14,
                            color = LocalAppTheme.colors.accentText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}