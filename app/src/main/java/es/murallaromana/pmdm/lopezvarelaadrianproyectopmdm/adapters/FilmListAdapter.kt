package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.activities.FilmDetailsActivity
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FilmItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FilmListAdapter(val films : List<Film>) : RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>(){

    private var filmPosterWidth : Int = 0
    private var filmPosterHeight: Int = 0

    private val dateFormat : DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    class FilmViewHolder(binding : FilmItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        // TODO(change the binding to a method inside the holder, instead of directing binding in Adapter.onBindViewHolder)
        val tvTitle = binding.tvFilmTitle
        val tvReleaseDate = binding.tvReleaseDate
        val tvDirector = binding.tvDirector
        val ivFilmPoster = binding.ivFilmPoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        filmPosterWidth = (parent.context.resources.getDimension(R.dimen.film_poster_list_width)).toInt()
        filmPosterHeight = (parent.context.resources.getDimension(R.dimen.film_poster_list_height)).toInt()
        val binding = FilmItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        with(holder){
            tvTitle.setText(film.title)
            tvDirector.setText("Directed by: " + film.director)
            tvReleaseDate.setText("Released on: " + dateFormat.format(film.releaseDate))
            Picasso.get().load(film.imageURL)
                .resize(filmPosterWidth, filmPosterHeight).centerCrop()
                .into(ivFilmPoster)
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