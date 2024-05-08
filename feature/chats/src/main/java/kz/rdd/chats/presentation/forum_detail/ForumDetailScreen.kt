package kz.rdd.chats.presentation.forum_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.chats.presentation.forum.ForumComponent
import kz.rdd.chats.presentation.forum.ForumViewModel
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.time.format.DateTimeFormatter

@Parcelize
class ForumDetailDestination(
    private val id: Int,
) : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<ForumDetailViewModel>{
            parametersOf(id)
        }
        ForumDetailScreen(viewModel)
    }
}

@Composable
fun ForumDetailScreen(
    viewModel: ForumDetailViewModel
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeNavigationPadding(),
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
        ) {
            state.forumList.forEach {
                ForumComponent(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    stars = it.stars,
                    userName = it.userName,
                    description = it.description,
                    onClick = { }
                )
            }
        }

        Text(
            text = "Commentaries",
            style = LocalAppTheme.typography.l17,
            color = LocalAppTheme.colors.primaryText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            state.forumMessages.forEach {
                val date = it.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
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
                            text = it.username,
                            style = LocalAppTheme.typography.l14B,
                            color = LocalAppTheme.colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = date,
                            style = LocalAppTheme.typography.l14B,
                            color = LocalAppTheme.colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Text(
                        text = it.message,
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText,
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CommonTextField(
            value = state.currentMessage,
            onUpdate = viewModel::onUpdateMessage,
            placeholderText = "Send Message",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        CommonButton(
            text = "Send",
            onClick = viewModel::onClickSend,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}