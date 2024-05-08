package kz.rdd.login.presentation.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.safeStatusBarPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun IntroductionScreen(
    onClickSkip: () -> Unit,
    onClickNext: () -> Unit,
    image: Painter,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1)
            .safeNavigationPadding()
            .safeStatusBarPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 34.dp)
                .fillMaxHeight(0.1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Skip",
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.accentText,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.alphaClickable { onClickSkip() }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        ) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier
                    .size(550.dp)
                    .fillMaxSize()

            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = LocalAppTheme.typography.l24,
                color = LocalAppTheme.colors.primaryText,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.primaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .background(LocalAppTheme.colors.white, CircleShape)
                    .scaledClickable {
                        onClickNext()
                    }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_20),
                    contentDescription = "",
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))
    }
}





















