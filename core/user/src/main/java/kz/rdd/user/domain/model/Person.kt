package kz.rdd.core.user.domain.model

import kz.rdd.core.utils.ext.getFullName

data class Person(
    val id: Int,
    val position: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val avatar: String?,
) {
    val fullName = getFullName(
        firstName = firstName,
        lastName = lastName,
        middleName = middleName
    )
}
