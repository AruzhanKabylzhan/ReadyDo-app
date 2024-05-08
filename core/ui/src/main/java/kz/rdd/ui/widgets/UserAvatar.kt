package kz.rdd.core.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.ext.withShimmer
import kz.rdd.core.ui.screen.image_zoom.clickOpenFullImageContent
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.theme.PreviewAppTheme

@Composable
fun UserAvatar(
    url: String?,
    score: Int,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
) {
    Box(modifier = modifier) {
        CommonAsyncImage(
            url = url,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .border(2.dp, score.getScoreColor(), CircleShape)
                .clickOpenFullImageContent(url)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
            error = painterResource(id = R.drawable.ic_user_photo_solo),
        )
        UserScore(
            score = score,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BusinessmanAvatar(
    url: String?,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    tariffName: String = "",
    tariffBrush: Brush,
    tariffTextColor: Color,
    onClickTariff: () -> Unit,
) {
    Box(modifier = modifier) {
        CommonAsyncImage(
            url = url,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .clickOpenFullImageContent(url)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
            error = painterResource(id = R.drawable.ic_user_photo_solo),
        )
        AnimatedVisibility(
            visible = tariffName.isNotEmpty(),
            enter = fadeIn() + scaleIn(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                )
            ),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BusinessmanTariffName(
                tariffName = tariffName,
                tariffBrush = tariffBrush,
                tariffTextColor = tariffTextColor,
                modifier = Modifier
                    .alphaClickable { onClickTariff() }
            )
        }
    }
}

@Composable
fun UserAvatarShimmer(
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
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(size * 0.7f)
                .background(LocalAppTheme.colors.shimmer)
                .withShimmer()
        )
    }
}

@Composable
@Preview
private fun Preview() {
    PreviewAppTheme {
        UserAvatar(url = "", score = 100)
    }
}
