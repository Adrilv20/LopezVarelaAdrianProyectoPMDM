package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.LoginData
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Token
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @GET("movies")
    fun getPeliculas(): Call<List<Film>>

    @POST("users/singup")
    fun userSingUp(@Body user : UserData): Call<Unit>

    @POST("users/login")
    fun userLogIn(@Body loginData : LoginData) : Call<Token>

    // TODO: declarar todos los métodos del API siguiendo la documentación
}