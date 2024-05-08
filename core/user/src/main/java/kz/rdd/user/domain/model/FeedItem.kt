package kz.rdd.core.user.domain.model

import java.time.OffsetDateTime

data class FeedItem(
    val startDate: OffsetDateTime,
    val fullName: String,
    val avatar: String?,
    val score: Int,
    val reason: String,
    val points: Int,
)