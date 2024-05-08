package kz.rdd.core.user.data.mapper

import kz.rdd.core.user.data.model.PersonDto
import kz.rdd.core.user.domain.model.Person
import kz.rdd.core.utils.exception.orMappingExceptionOn

class PersonMapper {
    fun map(dto: PersonDto) = Person(
        id = dto.id.orMappingExceptionOn("id", dto),
        position = dto.position.orEmpty(),
        firstName = dto.firstName.orEmpty(),
        middleName = dto.middleName.orEmpty(),
        lastName = dto.lastName.orEmpty(),
        avatar = dto.avatar,
    )
}
