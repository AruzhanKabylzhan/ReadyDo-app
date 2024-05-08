package kz.rdd.catalog.presentation.meal_detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize
import kz.rdd.catalog.domain.Food
import kz.rdd.core.ui.R
import kz.rdd.catalog.presentation.filter.FilterViewModel
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class MealDetailDestination(
    private val food: Food,
) : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<MealDetailViewModel>{
            parametersOf(food)
        }
        MealDetailScreen(
            viewModel = viewModel
        )
    }
}

@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {

        CenteredToolbar(
            title = state.title,
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack,
            actions = {
                Icon(
                    imageVector = ImageVector.vectorResource(if(state.favorite) R.drawable.ic_liked else R.drawable.ic_like),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.error,
                    modifier = Modifier.size(32.dp).scaledClickable { viewModel.onClickAddToFav() }
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            CommonAsyncImage(
                url = state.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = state.title,
                    style = LocalAppTheme.typography.l20.copy(
                        fontSize = 30.sp
                    ),
                    color = LocalAppTheme.colors.white,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_users_24),
                        contentDescription = "",
                        tint = LocalAppTheme.colors.white,
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = state.userName,
                        style = LocalAppTheme.typography.l14B,
                        color = LocalAppTheme.colors.white,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(state.stars) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                                contentDescription = "",
                                tint = LocalAppTheme.colors.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Text(
                    text = state.price.toString().plus("$"),
                    style = LocalAppTheme.typography.l24.copy(
                        fontSize = 32.sp
                    ),
                    color = LocalAppTheme.colors.white,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingredients",
            style = LocalAppTheme.typography.l20,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = state.description,
            style = LocalAppTheme.typography.l14,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(LocalAppTheme.colors.secondary, RoundedCornerShape(12.dp))
                    .scaledClickable {
                        viewModel.onClickBusket(state.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_basket),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.white,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}