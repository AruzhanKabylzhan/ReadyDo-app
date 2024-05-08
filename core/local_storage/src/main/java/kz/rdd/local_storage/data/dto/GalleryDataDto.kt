package kz.rdd.core.local_storage.data.dto

data class GalleryDataDto(
    val id: Long,
    val title: String,
    val albumName: String,
    val dataUri: String,
    val mediaType: Int,
    val duration: Long,
    val dateAdded: String?
)
