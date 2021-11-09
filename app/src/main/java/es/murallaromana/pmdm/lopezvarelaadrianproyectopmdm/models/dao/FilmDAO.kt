package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film

interface FilmDAO {
    fun getallFilms() : List<Film>
}