package kz.rdd.core.user.data.mapper

import kz.rdd.core.user.data.model.EmployeesListDto
import kz.rdd.core.user.domain.model.EmployeesProfile

class EmployeesListMapper {
    fun map(dto: EmployeesListDto): EmployeesProfile {
        return EmployeesProfile(
            result = dto.results?.map {
                EmployeesProfile.Result(
                    department = EmployeesProfile.Result.Department(
                        id = it.department?.id ?: 0,
                        name = it.department?.name.orEmpty()
                    ),
                    grade = it.grade ?: 1,
                    id = it.id ?: 0,
                    role = it.role ?: 0,
                    score = it.score,
                    scorePrevious = it.scorePrevious ?: 0,
                    title = it.title.orEmpty(),
                    user = EmployeesProfile.Result.User(
                        avatar = it.user?.avatar.orEmpty(),
                        firstName = it.user?.firstName.orEmpty(),
                        lastName = it.user?.lastName.orEmpty(),
                        id = it.id ?: 0
                    )
                )
            }.orEmpty()
        )
    }
}