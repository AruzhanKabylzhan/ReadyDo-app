package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun BaseDialogContainer(
    onClickBack: () -> Unit,
    horizontalPadding: Dp = 22.dp,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onClickBack,
                modifier = Modifier
                    .background(LocalAppTheme.colors.white, shape = CircleShape)
                    .clip(shape = CircleShape),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_black_close),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.black,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = LocalAppTheme.colors.white,
            elevation = 0.dp,
            content = content,
        )
    }
}