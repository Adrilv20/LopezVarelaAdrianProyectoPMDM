package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.LoginData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Token
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("movies")
    fun getPeliculas(): Call<List<Film>>

    @Headers("No-Authentication: true")
    @POST("users/signup")
    fun userSingUp(@Body user : UserData): Call<Unit>

    @Headers("No-Authentication: true")
    @POST("users/login")
    fun userLogIn(@Body loginData : LoginData) : Call<Token>

    // TODO: declarar todos los métodos del API siguiendo la documentación
}