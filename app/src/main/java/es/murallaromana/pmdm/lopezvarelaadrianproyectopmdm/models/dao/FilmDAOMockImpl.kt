package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.LocalDate

class FilmDAOMockImpl: FilmDAO {
    override fun getallFilms(): List<Film> =
        listOf<Film>(
            Film("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson",
                LocalDate.of(2001,12,19), ""),
            Film("The Lord of the Rings: The Two Towers", "Peter Jackson",
                LocalDate.of(2002,12,18), ""),
            Film("The Lord of the Rings: The Return of the King", "Peter Jackson",
                LocalDate.of(2003,12,17), "")
        )
}