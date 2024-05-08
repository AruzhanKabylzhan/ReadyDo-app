package kz.rdd.core.ui.screen.zoomable_image

import android.net.Uri
import androidx.core.net.toUri
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kz.rdd.core.local_storage.data.dto.StorageFileType
import kz.rdd.core.local_storage.domain.LocalStorage
import kz.rdd.core.ui.base.navigation.ShowDialog
import kz.rdd.core.ui.base.viewmodel.UiEvent
import kz.rdd.core.ui.screen.loading_indicator.LoadingIndicatorDialogDestination
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Parcelize
internal class LoadingIndicatorUrlBehavior(
    private val url: String?,
) : LoadingIndicatorDialogDestination.Behavior, KoinComponent {

    @IgnoredOnParcel
    private val localStorage by inject<LocalStorage>()
    override suspend fun load(
        setProgress: (Float) -> Unit,
        onSuccessEvent: (UiEvent) -> Unit
    ) {
        if (url.isNullOrEmpty()) {
            throwError()
        }
        val fileType = StorageFileType.IMAGE
        val extension = url.split(".").lastOrNull() ?: fileType.format
        val fileName = url.replace("[:/.]".toRegex(), "_") + ".$extension"
        val onSuccess = { uri: Uri ->
            onSuccessEvent(
                ShowDialog(
                    ZoomableImageDialogDestination(
                        ZoomableImageDialogDestination.Behavior.UriOpener(uri)
                    ),
                    replace = true,
                )
            )
        }
        val checkFile = localStorage.getTempFile(fileType, fileName)
        if (checkFile.exists()) {
            setProgress(1f)
            onSuccess(checkFile.toUri())
            return
        }
        val (_, uri) = localStorage.downloadFromUrl(
            url = url,
            storageFileType = fileType,
            progressListener = {
                setProgress(it)
            },
            fileName = fileName,
        ) ?: throwError()

        onSuccess(uri)
    }
}