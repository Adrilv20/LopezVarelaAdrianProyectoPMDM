package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.interceptors

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.SessionManager
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Custom interceptor to include the auth token on all API calls that don't have the No-Authentication header
 */
class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // case no auth header needed
        if (chain.request().header("No-Authentication") != null) {
            return chain.proceed(chain.request())
        }
        val requestBuilder = chain.request().newBuilder()
        SessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization","Bearer $it")
        } ?: chain.call().cancel() // shouldn't actually happen assuming proper use of SessionManager

        return chain.proceed(requestBuilder.build())
    }
}