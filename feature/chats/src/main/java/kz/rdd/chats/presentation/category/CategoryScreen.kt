package kz.rdd.chats.presentation.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.navigate.manual.MapContentDelegate
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object CategoryContentDelegateImpl : MapContentDelegate {
    @Composable
    override fun Content() {
        CategoryScreen()
    }
}

@Composable
fun CategoryScreen() {
    val viewModel = getViewModel<CategoryViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        CenteredToolbar(
            title = "Chats",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = false,
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            ManualItem(
                image = painterResource(id = R.drawable.chat),
                title = "Chat AI",
                onClickManual = { viewModel.onClickCategory(1) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ManualItem(
                image = painterResource(id = R.drawable.forumforum),
                title = "Forum",
                onClickManual = { viewModel.onClickCategory(2) }
            )
        }
    }
}

@Composable
fun ColumnScope.ManualItem(
    image: Painter,
    title: String,
    onClickManual: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .scaledClickable { onClickManual() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Row(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            LocalAppTheme.colors.gray3,
                            LocalAppTheme.colors.black
                        )
                    ), RoundedCornerShape(12.dp), alpha = 0.2f
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            androidx.compose.material.Text(
                text = title,
                style = LocalAppTheme.typography.l24,
                color = LocalAppTheme.colors.white,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                contentDescription = "",
                tint = LocalAppTheme.colors.white,
                modifier = Modifier.size(36.dp).rotate(180f)
            )
        }
    }
}

@Composable
fun CategoryComponent(
    id: Int,
    title: String,
    imageUrl: Any?,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scaledClickable { onClick(id) },
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .background(LocalAppTheme.colors.white, RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CommonAsyncImage(
                url = imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = LocalAppTheme.typography.l17,
                    color = LocalAppTheme.colors.primaryText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }
}