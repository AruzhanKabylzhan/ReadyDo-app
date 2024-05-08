package kz.rdd.core.user.domain.model

class EmployeesProfile(
    val result: List<Result>
) {
    class Result(
        val department: Department,
        val grade: Int,
        val id: Int,
        val role: Int,
        val score: Int?,
        val scorePrevious: Int,
        val title: String,
        val user: User
    ) {
        class User(
            val avatar: String,
            val firstName: String,
            val id: Int,
            val lastName: String,
        ) {
            fun doesMatchSearchQuery(query: String): Boolean {
                val matchingCombinations = listOf(
                    "$firstName$lastName",
                    "$firstName $lastName",
                    "${firstName.first()} ${lastName.first()}",
                )

                return matchingCombinations.any {
                    it.contains(query, ignoreCase = true)
                }
            }
        }

        class Department(
            val id: Int,
            val name: String
        )
    }
}