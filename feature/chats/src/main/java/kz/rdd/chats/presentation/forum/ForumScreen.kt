package kz.rdd.chats.presentation.forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.chats.presentation.category.CategoryComponent
import kz.rdd.chats.presentation.category.CategoryViewModel
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
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Parcelize
class ForumDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        ForumScreen()
    }
}

@Composable
fun ForumScreen(){
    val viewModel = getViewModel<ForumViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        CenteredToolbar(
            title = "Forum",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            state.forumList.forEach {
                ForumComponent(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    stars = it.stars,
                    userName = it.userName,
                    description = it.description,
                    onClick = viewModel::onClickForum
                )
            }
        }
    }
}

@Composable
fun ForumComponent(
    id: Int,
    title: String,
    imageUrl: String,
    stars: Int,
    userName: String,
    description: String,
    onClick: (Int) -> Unit,
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(220.dp, 220.dp)
            .padding(vertical = 12.dp)
            .scaledClickable { onClick(id) },
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
    ) {
        Column(
            modifier = Modifier
                .background(LocalAppTheme.colors.white, RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                CommonAsyncImage(
                    url = imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

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

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(stars) {
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

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_users_24),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.secondary,
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = userName,
                    style = LocalAppTheme.typography.l14B,
                    color = LocalAppTheme.colors.primaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.weight(1f))
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = description,
                    style = LocalAppTheme.typography.l14,
                    color = LocalAppTheme.colors.accentText,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}