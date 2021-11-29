package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao

import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.LocalDate

class FilmDAOMockImpl: FilmDAO {
    override fun getAllFilms(): List<Film> =
        listOf<Film>(
            Film(1, "The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson","123456789",  178,
                LocalDate.of(2001,12,19), "https://flxt.tmsimg.com/assets/p28828_p_v10_am.jpg",
            "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron."),
            Film(2, "The Lord of the Rings: The Two Towers", "Peter Jackson", "123456789", 179,
                LocalDate.of(2002,12,18), "https://flxt.tmsimg.com/NowShowing/28574/28574_ab.jpg",
                "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard."),
            Film(3, "The Lord of the Rings: The Return of the King", "Peter Jackson","123456789",  201,
                LocalDate.of(2003,12,17), "https://flxt.tmsimg.com/assets/p33156_p_v10_ab.jpg",
            "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."),
            Film(4, "Forrest Gump", "Robert Zemeckis", "987654321", 142, LocalDate.of(1994,  7,6),
                "https://flxt.tmsimg.com/NowShowing/3509/3509_aa.jpg", "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart."),
        )
}