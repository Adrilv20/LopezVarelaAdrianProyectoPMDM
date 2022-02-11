package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.R
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.actionHandlers.ClickHandler
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.databinding.FilmItemListBinding
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.models.entities.Film
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.RetrofitClient
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateToString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmListAdapter(private var films: MutableList<Film>, val listActivity : AppCompatActivity) :
    RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {
    // context required in order to fetch the dimensions for the images
    private var filmPosterWidth: Int =
        listActivity.resources.getDimension(R.dimen.film_poster_list_width).toInt()
    private var filmPosterHeight: Int =
        listActivity.resources.getDimension(R.dimen.film_poster_list_height).toInt()

    class FilmViewHolder(binding: FilmItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        // TODO(change the binding to a method inside the holder, instead of directing binding in Adapter.onBindViewHolder)
        val tvTitle = binding.tvFilmTitle
        val tvReleaseDate = binding.tvReleaseDate
        val tvDirector = binding.tvDirector
        val ivFilmPoster = binding.ivFilmPoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        // This method may be called multiple times (for example when scrolling down the list)
        // Thus the retrieval of the dimensions for the images has been changed to be performed only when FilmListAdapter gets created.
        val binding =
            FilmItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        // load the info of the corresponding film (based on it's position on the RecyclerView)
        // on the corresponding holder
        val film = films[position]
        with(holder) {
            tvTitle.text = film.title
            tvDirector.text =
                film.director?.let { listActivity.getString(R.string.directed_by_prefix) + " " + it }
            tvReleaseDate.text =
                film.releaseDate?.run {
                    listActivity.getString(R.string.releasedOnPrefix) + ": " + dateToString(this)
                }

            try {
                Picasso.get().load(film.imageURL)
                    .resize(filmPosterWidth, filmPosterHeight).centerCrop()
                    .into(ivFilmPoster)
            } catch (ex: IllegalArgumentException) {
                ivFilmPoster.setImageResource(R.drawable.ic_image_not_found)
            }

        }

        // set the listener to navigate to the details page of the film
        holder.itemView.setOnClickListener {
            (listActivity as ClickHandler).onItemClickHandler(position)
        }

        // set delete mechanism
        holder.itemView.setOnLongClickListener {
            (listActivity as ClickHandler).onItemLongClickHandler(position)
        }
    }

    override fun getItemCount(): Int = films.size


}