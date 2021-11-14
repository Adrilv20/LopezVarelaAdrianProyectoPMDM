package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities.FilmDetailsActivity
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FilmItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmListAdapter(val films : List<Film>) : RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>(){

    private val dateFormat : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    class FilmViewHolder(binding : FilmItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        // TODO(change the binding to a method inside the holder, instead of directing binding in Adapter.onBindViewHolder)
        val tvTitle = binding.tvFilmTitle
        val tvReleaseDate = binding.tvReleaseDate
        val tvDirector = binding.tvDirector
        val ivFilmPoster = binding.ivFilmPoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FilmItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        with(holder){
            tvTitle.setText(film.title)
            tvDirector.setText("Directed by: " + film.director)
            tvReleaseDate.setText("Released on: " + dateFormat.format(film.releaseDate))
            // TODO(add images with Picasso library)
        }
        holder.itemView.setOnClickListener {
            val intent : Intent = Intent(holder.itemView.context, FilmDetailsActivity::class.java).apply{
                putExtra("film", film)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = films.size

}