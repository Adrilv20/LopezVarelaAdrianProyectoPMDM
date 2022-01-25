package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://damapi.herokuapp.com/api/v1/"

object RetrofitClient {
    private fun createRetrofitClient() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    val apiRetrofit: API = createRetrofitClient().create(API::class.java)
}