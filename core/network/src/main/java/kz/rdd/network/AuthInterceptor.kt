package kz.rdd.core.network

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kz.rdd.core.utils.ext.withBearer
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.store.UserLogoutCleanUseCase
import kz.rdd.store.UserStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

class AuthInterceptor(
    private val tokenUseCase: TokenUseCase,
    private val userStore: UserStore,
    private val userLogoutCleanUseCase: UserLogoutCleanUseCase,
) : Interceptor {

    private val mutex = Mutex()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val token = getToken()
        val refreshToken = userStore.refreshToken.orEmpty()
        if (token.isNotEmpty() || refreshToken.isNotEmpty()) {
            // Token is fresh or refresh token exists
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeaders(token)
            val originalRequest = chain.request()
            val response = chain.proceed(requestBuilder.build())
            // If token is expired or anyhow received unauthorized then try to refresh
            if (
                response.code == HttpsURLConnection.HTTP_UNAUTHORIZED
                && token.isNotEmpty()
            ) {
                response.close()
                mutex.withLock {
                    if (updateTokens()) {
                        // Token refreshed, try again
                        val newCall =
                            chain.request().newBuilder().addHeaders(getToken()).build()
                        chain.proceedDeletingTokenOnError(newCall)
                    } else {
                        // Token was expired and can't be refreshed, return
                        chain.proceedDeletingTokenOnError(originalRequest)
                    }
                }
            } else {
                // Response was successful
                response
            }
        } else {
            // Token has expired and there is no refresh token
            chain.proceedDeletingTokenOnError(chain.request())
        }
    }

    private fun getToken(): String {
        return userStore.accessToken.orEmpty()
    }

    private suspend fun updateTokens(): Boolean = runBlocking {
        val refreshToken = userStore.refreshToken ?: return@runBlocking false
        val result = tokenUseCase.refreshToken(refreshToken)
        result is Outcome.Success
    }

    private suspend fun Interceptor.Chain.proceedDeletingTokenOnError(request: Request): Response {
        val response = proceed(request)
        if (
            response.code == HttpsURLConnection.HTTP_UNAUTHORIZED
            || response.code == HttpsURLConnection.HTTP_FORBIDDEN
            || response.code == HttpsURLConnection.HTTP_BAD_REQUEST
        ) {
            userLogoutCleanUseCase.cleanAll()
        }
        return response
    }

    private fun Request.Builder.addHeaders(
        token: String
    ) = this.apply { header("Authorization", token.withBearer) }

}