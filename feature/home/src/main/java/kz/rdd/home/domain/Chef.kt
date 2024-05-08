package kz.rdd.home.domain

data class Chef (
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val avatar: String?,
    val grade: String,
)