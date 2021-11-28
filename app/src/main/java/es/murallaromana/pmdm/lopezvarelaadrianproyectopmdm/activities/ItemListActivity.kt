package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.App.GLB_STATE
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters.FilmListAdapter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityItemListBinding
    private lateinit var view : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the screen title
        title = resources.getString(R.string.filmListViewTitle)

        val fab : FloatingActionButton = binding.fabAddItem
        fab.setOnClickListener {
            val newFilmIntent = Intent(this, FilmEditActivity::class.java)
            startActivity(newFilmIntent)
        }

    }

    override fun onResume() {
        super.onResume()

        // retrieve the films data
        val films = GLB_STATE.getAllFilms()
        // set the layout manager and the adapter for the RecyclerView
        view = binding.rvFilmList
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = FilmListAdapter(films, this)
        // optimization for the RecyclerView rendering
        view.setHasFixedSize(true)
    }
}