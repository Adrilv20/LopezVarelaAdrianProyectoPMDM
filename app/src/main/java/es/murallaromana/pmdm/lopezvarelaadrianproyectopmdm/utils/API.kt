package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.LoginData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Token
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("movies")
    fun getFilms(): Call<List<Film>>

    @GET("movies/{id}")
    fun getFilmById(@Path("id") id: String) : Call<Film>

    @POST("movies/")
    fun createFilm(@Body film : Film) : Call<Film>

    @PUT("movies/")
    fun editFilm(@Body film : Film)

    @DELETE("movies/{id}")
    fun deleteFilm(@Path("id") id : String)

    @Headers("No-Authentication: true")
    @POST("users/signup")
    fun userSingUp(@Body user : UserData): Call<Unit>

    @Headers("No-Authentication: true")
    @POST("users/login")
    fun userLogIn(@Body loginData : LoginData) : Call<Token>
}