package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("m ovies")
    fun getPeliculas(): Call<List<Film>>

    // TODO: declarar todos los métodos del API siguiendo la documentación
}