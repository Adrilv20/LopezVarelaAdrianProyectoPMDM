package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.LocalDate

class FilmDAOMockImpl: FilmDAO {
    override fun getallFilms(): List<Film> =
        listOf<Film>(
            Film("The Lord of the Rings: The Two Towers", "Peter Jackson", 179,
                LocalDate.of(2002,12,18), "https://flxt.tmsimg.com/NowShowing/28574/28574_ab.jpg"),
            Film("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 178,
                LocalDate.of(2001,12,19), "https://flxt.tmsimg.com/assets/p28828_p_v10_am.jpg"),
            Film("The Lord of the Rings: The Return of the King", "Peter Jackson", 201,
                LocalDate.of(2003,12,17), "https://flxt.tmsimg.com/assets/p33156_p_v10_ab.jpg")
        )
}