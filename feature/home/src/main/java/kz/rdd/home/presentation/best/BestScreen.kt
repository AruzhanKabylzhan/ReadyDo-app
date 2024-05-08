package kz.rdd.home.presentation.best

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.home.presentation.HomeViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
data class BestParams(
    val isChefs: Boolean,
) : Parcelable


@Parcelize
class BestDestination(
    private val bestParams: BestParams,
) : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<BestViewModel> {
            parametersOf(bestParams)
        }
        BestScreen(viewModel)
    }

}

@Composable
fun BestScreen(
    viewModel: BestViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.white)
    ) {
        val title = if (state.value.isChefs) "The Best Chefs" else "The Best Meals"
        CenteredToolbar(
            title = title,
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack,
            backgroundColor = LocalAppTheme.colors.white
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
        ) {
            items(state.value.bestList) {
                Spacer(modifier = Modifier.height(5.dp))
                BestComponent(
                    best = it,
                    onClick = { }
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun BestComponent(
    best: Bests,
    onClick: () -> Unit,
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
                url = best.image,
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
                    text = best.username,
                    style = LocalAppTheme.typography.l14B,
                    color = LocalAppTheme.colors.primaryText
                )

                Text(
                    text = best.fullName,
                    style = LocalAppTheme.typography.l13,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(best.stars) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                        contentDescription = "",
                        tint = LocalAppTheme.colors.secondary,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}