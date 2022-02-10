package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import com.google.gson.*
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.adapters.LocalDateAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

val BASE_URL = "https://damapi.herokuapp.com/api/v1/"

/**
 * Singleton that configures and stores the retrofit instances for the app.
 */
object RetrofitClient {
    /** configures the retrofit instance to be used by the whole app from this singleton */
    private fun createRetrofitClient(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(customHTTPClient)
            .build()
        return retrofit
    }

    // making a specific gson instance to include an adapter to support the API date format
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    // NOT IN USE. Left here for reviewing purposes.
//    val gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java, DateDeserializer()).create()

    /**
     * Keeping the factory in case we need more instances to make sure we always use the same Gson instance
     * allegedly, Gson has it's own kind of cache for structures
     */
    private val gsonFactory = GsonConverterFactory.create(gson)


    /** Keeping the HTTPClient separately to share it in case a different retrofit client is needed. */
    private val customHTTPClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    val instance: API = createRetrofitClient().create(API::class.java)
}