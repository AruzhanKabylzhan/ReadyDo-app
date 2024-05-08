package kz.rdd.core.user.data.network

import kz.rdd.core.user.data.model.BusinessmanUserProfileDto
import kz.rdd.core.user.data.model.EmployeesListDto
import kz.rdd.core.user.data.model.PersonDto
import kz.rdd.core.user.data.model.EmployeeUserProfileDto
import kz.rdd.core.utils.outcome.Outcome
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserApi {
    @GET("profile/employee/")
    suspend fun getEmployeeUserProfile(): Outcome<EmployeeUserProfileDto>

    @GET("profile/owner/")
    suspend fun getBusinessmanUserProfile(): Outcome<BusinessmanUserProfileDto>

    @GET("employees/")
    suspend fun getEmployeesList(
        @Query("company") company: String,
        @Query("departments") departments: List<Int>
    ): Outcome<EmployeesListDto>

    @GET("company/{id}/users")
    suspend fun getCompanyUsers(
        @Path("id") company: String,
    ): Outcome<List<PersonDto>>
}
