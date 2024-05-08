package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.theme.PreviewAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredContentToolbar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_24),
    isNavigationIconVisible: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = LocalAppTheme.colors.white,
    navigationIconTint: Color = LocalAppTheme.colors.iconBlack,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor.copy(alpha = 0.8f)
        ),
        windowInsets = windowInsets,
        navigationIcon = if (isNavigationIconVisible) {
            {
                IconButton(
                    onClick = { onNavigationIconClick?.invoke() },
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                        tint = navigationIconTint,
                    )
                }
            }
        } else {
            { }
        },
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredToolbar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_24),
    isNavigationIconVisible: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = LocalAppTheme.colors.white,
    titleColor: Color = LocalAppTheme.colors.primaryText,
    navigationIconTint: Color = LocalAppTheme.colors.iconBlack,
    titleStyle: TextStyle = LocalAppTheme.typography.h1,
    titleModifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets
) {
    CenteredContentToolbar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = titleStyle,
                color = titleColor,
                modifier = titleModifier,
                textAlign = TextAlign.Center,
            )
        },
        modifier = modifier,
        onNavigationIconClick = onNavigationIconClick,
        navigationIcon = navigationIcon,
        isNavigationIconVisible = isNavigationIconVisible,
        actions = actions,
        backgroundColor = backgroundColor,
        navigationIconTint = navigationIconTint,
        windowInsets = windowInsets,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_24),
    isNavigationIconVisible: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = LocalAppTheme.colors.white,
    titleColor: Color = LocalAppTheme.colors.primaryText,
    navigationIconTint: Color = LocalAppTheme.colors.iconBlack,
    titleStyle: TextStyle = LocalAppTheme.typography.h1,
    titleModifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = titleStyle,
                color = titleColor,
                modifier = titleModifier,
                textAlign = TextAlign.Center,
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        colors = topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor.copy(alpha = 0.8f)
        ),
        windowInsets = windowInsets,
        navigationIcon = if (isNavigationIconVisible) {
            {
                IconButton(
                    onClick = { onNavigationIconClick?.invoke() },
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                        tint = navigationIconTint,
                    )
                }
            }
        } else {
            { }
        },
        actions = actions,
    )
}

@Preview
@Composable
fun ToolbarPreview() {
    PreviewAppTheme {
        CenteredToolbar(title = "Title")
    }
}

@Preview
@Composable
fun ToolbarPreview2() {
    PreviewAppTheme {
        CenteredToolbar(title = "")
    }
}