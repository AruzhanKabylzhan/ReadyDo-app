package kz.rdd.login.domain.model

class VerifySmsData(
    val token: String,
)

class VerifySmsSuccessData(
    val message: String,
)

enum class VerifySmsTypes{
    OK
}
