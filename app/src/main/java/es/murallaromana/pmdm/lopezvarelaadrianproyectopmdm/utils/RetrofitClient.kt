package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://damapi.herokuapp.com/api/v1/"

object RetrofitClient {
    private fun createRetrofitClient() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(customHTTPClient)
            .build()
        return retrofit
    }

    private val customHTTPClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    val instance: API = createRetrofitClient().create(API::class.java)
}