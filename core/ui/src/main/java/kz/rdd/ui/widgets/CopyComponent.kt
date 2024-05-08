package kz.rdd.core.ui.widgets

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.ext.copyText
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CopyComponent(
    inviteLink: String,
    textStyle: TextStyle = LocalAppTheme.typography.l16,
    onClickLink: (() -> Unit)? = null,
) {
    val context: Context = LocalContext.current

    val counter = remember { mutableStateOf(0) }
    val isClicked = remember { mutableStateOf(false) }

    LaunchedEffect(isClicked.value) {
        if (isClicked.value) {
            val from = 3
            val to = 0
            launch(Dispatchers.Default) {
                for (count in (from downTo to)) {
                    counter.value = count
                    if (count != 0) delay(1000L)
                }
                isClicked.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .border(1.dp, LocalAppTheme.colors.stroke, RoundedCornerShape(12.dp))
            .background(LocalAppTheme.colors.white, RoundedCornerShape(12.dp))
            .heightIn(min = 48.dp, max = 48.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = inviteLink,
                style = textStyle,
                color = LocalAppTheme.colors.primaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(end = 8.dp)
                    .alphaClickable {
                        onClickLink?.let { it() }
                    }
            )

            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(LocalAppTheme.colors.stroke),
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (counter.value > 0) {
                Column(
                    modifier = Modifier
                        .background(LocalAppTheme.colors.checkGreen, CircleShape)
                        .height(24.dp)
                        .width(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_check_16),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier
                    )
                }
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_file_copy_24),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .scaledClickable {
                            context.copyText(inviteLink)
                            counter.value = 3
                            isClicked.value = true
                        }
                )
            }
        }
    }
}