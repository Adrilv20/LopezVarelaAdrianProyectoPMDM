package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters.FilmListAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.dao.FilmDAOMockImpl

class ItemListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityItemListBinding
    private lateinit var view : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fab : FloatingActionButton = binding.fabAddItem
        fab.setOnClickListener {
            Toast.makeText(this, "to be implemented", Toast.LENGTH_LONG).show()
        }

        // retrieve the films
        val films = FilmDAOMockImpl().getallFilms()
        // set the layout manager for the RecyclerView
        view = binding.rvFilmList
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = FilmListAdapter(films)

        view.setHasFixedSize(true)
    }
}