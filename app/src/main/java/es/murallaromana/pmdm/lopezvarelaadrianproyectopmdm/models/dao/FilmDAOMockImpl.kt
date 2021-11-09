package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.LocalDate

class FilmDAOMockImpl: FilmDAO {
    override fun getallFilms(): List<Film> =
        listOf<Film>(
            Film("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson",
                LocalDate.of(2001,12,19), ""),
            Film("Film 2","Dir2", LocalDate.now(), ""),
        )


}