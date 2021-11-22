package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
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

        // set the screen title
        title = resources.getString(R.string.filmListViewTitle)

        // TODO(finish the listener to move to new film screen with the FAB)
        val fab : FloatingActionButton = binding.fabAddItem
        fab.setOnClickListener {
            Toast.makeText(this, "to be implemented", Toast.LENGTH_LONG).show()
        }

        // retrieve the films data
        val films = FilmDAOMockImpl().getallFilms()
        // set the layout manager and the adapter for the RecyclerView
        view = binding.rvFilmList
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = FilmListAdapter(films, this)
        // optimization for the RecyclerView rendering
        view.setHasFixedSize(true)
    }
}