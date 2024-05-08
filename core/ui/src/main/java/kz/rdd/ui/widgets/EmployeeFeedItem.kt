package kz.rdd.core.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.user.domain.model.FeedItem
import java.time.format.DateTimeFormatter

@Composable
fun EmployeeFeedItem(
    employeeFeedItem: FeedItem,
    onClick: () -> Unit
) {
    val startDateFormat = remember{employeeFeedItem.startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}
    val colorText = LocalAppTheme.colors.run {
        if (employeeFeedItem.points < 0) error else checkGreen
    }
    Column(
        modifier = Modifier
            .border(1.dp, color = LocalAppTheme.colors.stroke3, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .scaledClickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserShortHeader(
                roleName = stringResource(id = R.string.employee_feed_work_date, startDateFormat),
                fullName = employeeFeedItem.fullName,
                avatar = employeeFeedItem.avatar,
                score = employeeFeedItem.score,
            )

            UserScore(score = employeeFeedItem.score)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            ScoreInfo(score = employeeFeedItem.points, scorePrevious = 0)

            Text(
                text = employeeFeedItem.reason,
                style = LocalAppTheme.typography.l12,
                color = colorText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
private fun UserShortHeader(
    roleName: String,
    fullName: String,
    avatar: String?,
    score: Int,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CommonAsyncImage(
            url = avatar,
            modifier = Modifier
                .size(40.dp)
                .border(2.dp, score.getScoreColor(), CircleShape)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
            error = painterResource(id = R.drawable.ic_user_photo_solo),
        )
        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = fullName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = LocalAppTheme.typography.l14B,
                color = LocalAppTheme.colors.primaryText,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = roleName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = LocalAppTheme.typography.l12M,
                color = LocalAppTheme.colors.accentText,
            )
        }
    }
}