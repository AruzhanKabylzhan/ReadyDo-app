package kz.rdd.login.domain.model

class RegisterCompanyFormRequest(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val email: String,
    val phoneNumber: String,
    val companyName: String,
    val companyLegalName: String,
    val maxEmployeesQuantity: Int,
    val yearsOfWork: Int,
    val tariffId: Int = 1,
    val period: Int = 1,
)
