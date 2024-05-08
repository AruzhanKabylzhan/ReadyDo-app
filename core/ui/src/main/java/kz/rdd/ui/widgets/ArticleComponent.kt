package kz.rdd.core.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun ArticleComponent(
    id: Int,
    title: String,
    description: String,
    imageUrl: Any,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
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

                Text(
                    text = description,
                    style = LocalAppTheme.typography.l14,
                    color = LocalAppTheme.colors.accentText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }
}