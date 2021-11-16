package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.LocalDate

class FilmDAOMockImpl: FilmDAO {
    override fun getallFilms(): List<Film> =
        listOf<Film>(
            Film("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson",
                LocalDate.of(2001,12,19), "https://cdn.shopify.com/s/files/1/0057/3728/3618/products/b1b6860c465f64983d81a2ce14019d7e_cb04f573-c07e-4fc4-af7d-72fcb83623d6_480x.progressive.jpg"),
            Film("The Lord of the Rings: The Two Towers", "Peter Jackson",
                LocalDate.of(2002,12,18), "https://m.media-amazon.com/images/I/81eqQvveI6L._AC_SS450_.jpg"),
            Film("The Lord of the Rings: The Return of the King", "Peter Jackson",
                LocalDate.of(2003,12,17), "https://m.media-amazon.com/images/I/71X6YzwV0gL._AC_SS450_.jpg")
        )
}