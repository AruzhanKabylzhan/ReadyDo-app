package kz.rdd.core.utils.ext

fun <T, R> T.runIf (condition: Boolean, block: T.() -> R): R? {
    this.run {
        if (condition) {
            block()
        }
    }
    return null
}

fun getFullName(
    firstName: String?,
    lastName: String?,
    middleName: String?,
) = buildList(3) {
    firstName.takeIf { !it.isNullOrEmpty() }?.let(::add)
    lastName.takeIf { !it.isNullOrEmpty() }?.let(::add)
    middleName.takeIf { !it.isNullOrEmpty() }?.let(::add)
}.joinToString(" ")