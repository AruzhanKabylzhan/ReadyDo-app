package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.models.FileItem
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.ProgressIndicator
import kz.rdd.core.utils.ext.FileType

@Composable
fun FileItemWidget(
    fileItem: FileItem,
    modifier: Modifier = Modifier,
    onClickFile: (FileItem) -> Unit,
    onCloseClick: (FileItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.alphaClickable {
                onClickFile(fileItem)
            },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    when (fileItem.fileType) {
                        FileType.IMAGE -> R.drawable.ic_folder_image_24
                        FileType.VIDEO -> R.drawable.ic_folder_video_24
                        else -> R.drawable.ic_folder_doc
                    }
                ),
                contentDescription = null,
                tint = LocalAppTheme.colors.accentText,
            )
            Text(
                text = fileItem.fileName,
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.primaryText,
            )
        }
        Box(
            modifier = Modifier
                .alphaClickable {
                    onCloseClick(fileItem)
                }
                .padding(4.dp)
        ) {
            when (fileItem) {
                is FileItem.Loading -> {
                    ProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                    )
                }

                is FileItem.Loaded -> {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close_16),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}