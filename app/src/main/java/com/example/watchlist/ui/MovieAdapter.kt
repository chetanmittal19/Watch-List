package com.example.watchlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.watchlist.R
import com.example.watchlist.data.Movie
import com.example.watchlist.data.network.TmdbService

class MovieAdapter(private val listener: (Long) -> Unit) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemLayout, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View, private val listener: (Long) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val containerView: View = itemView
        private val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)

        fun bind(movie: Movie) {
            Glide.with(containerView)
                .load(TmdbService.POSTER_BASE_URL + movie.posterPath)
                .error(R.drawable.poster_placeholder)
                .into(moviePoster)

            movieTitle.text = movie.title

            itemView.setOnClickListener {
                listener.invoke(movie.id)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
