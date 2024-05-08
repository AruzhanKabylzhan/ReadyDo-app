package kz.rdd.core.utils

private const val PROD_BASE_URL = "https://ready-do.xyz/api/"
private const val DEV_BASE_URL = "https://ready-do.xyz/api/"

enum class ServerConfiguration(
    val baseUrl: String,
) {
    PROD(PROD_BASE_URL),
    DEV(DEV_BASE_URL);
}