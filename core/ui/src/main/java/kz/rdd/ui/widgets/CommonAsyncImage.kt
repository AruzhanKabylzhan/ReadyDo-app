package kz.rdd.core.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CommonAsyncImage(
    modifier: Modifier = Modifier,
    url: Any? = R.drawable.ic_user_photo_solo,
    placeholder: Painter? = ColorPainter(LocalAppTheme.colors.gray5),
    error: Painter? = ColorPainter(LocalAppTheme.colors.gray5),
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = placeholder,
        error = error,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
    )
}
