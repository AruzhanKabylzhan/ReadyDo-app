package kz.rdd.chats.domain

import java.time.OffsetDateTime

data class Forum(
    val id: Int,
    val name: String,
    val photo: String,
    val description: String,
    val username: String,
    val grade: String,
)

data class ForumMessage(
    val id: Int,
    val createdAt: OffsetDateTime,
    val message: String,
    val forum: Int,
    val username: String,
)
