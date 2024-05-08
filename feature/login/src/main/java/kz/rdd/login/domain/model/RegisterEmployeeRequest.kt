package kz.rdd.login.domain.model

class RegisterEmployeeRequest(
    val avatar: String?,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val phoneNumber: String,
    val grade: String,
    val address: String,
    val yourself: String,
    val username: String,
)
