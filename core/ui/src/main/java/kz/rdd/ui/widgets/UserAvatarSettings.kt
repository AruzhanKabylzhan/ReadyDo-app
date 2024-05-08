package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.ext.withShimmer
import kz.rdd.core.ui.screen.image_zoom.clickOpenFullImageContent
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun AccountUserAvatarSettings(
    url: Any?,
    onClickChangeUserAvatar: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = modifier
            .scaledClickable {
                onClickChangeUserAvatar()
            },
            contentAlignment = Alignment.Center
        ) {
            CommonAsyncImage(
                url = url,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(size)
                    .border(
                        width = 1.dp,
                        color = LocalAppTheme.colors.stroke,
                        shape = CircleShape
                    )
                    .clickOpenFullImageContent(url)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
                error = painterResource(id = R.drawable.ic_user_photo_solo),
            )

            Column(
                modifier = Modifier
                    .background(
                        color = LocalAppTheme.colors.settingsGreen,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd)
                    .clickableWithIndication {
                        onClickChangeUserAvatar()
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun AccountUserAvatarShimmer(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
) {

    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .border(2.dp, LocalAppTheme.colors.gray4, CircleShape)
                .clip(CircleShape)
                .background(LocalAppTheme.colors.shimmer)
                .withShimmer(),
        )

        Column(
            modifier = Modifier
                .background(
                    color = LocalAppTheme.colors.settingsGreen,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .padding(6.dp)
            )
        }
    }
}
