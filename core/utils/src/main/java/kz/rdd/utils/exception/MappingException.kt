package kz.rdd.core.utils.exception

class MappingException(
    val dtoStringRepresentation: String,
    val field: String
) : IllegalArgumentException() {

    override fun toString(): String {
        return "MappingException in field: $field, full dto: $dtoStringRepresentation"
    }
}

inline fun <reified T> T?.orMappingExceptionOn(
    field: String,
    dto: Any
): T {
    return this ?: throw MappingException(
        dtoStringRepresentation = dto.toString(),
        field = field,
    )
}
