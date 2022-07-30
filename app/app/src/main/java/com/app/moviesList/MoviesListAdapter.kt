package com.app.moviesList

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.model.Movie
import com.bumptech.glide.Glide

class MoviesListAdapter(private val onClickCard: (item: Movie) -> Unit) :
    ListAdapter<Movie, MoviesListAdapter.DataViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickCard)
    }

    class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imageMovie: ImageView = itemView.findViewById(R.id.image_movie)
        private val pg: TextView = itemView.findViewById(R.id.tv_pg)
        private val name: TextView = itemView.findViewById(R.id.tv_movie_name)
        private val review: TextView = itemView.findViewById(R.id.tv_review)
        private val duration: TextView = itemView.findViewById(R.id.tv_duration_movie)
        private val genre: TextView = itemView.findViewById(R.id.tv_genre)
        private val stars: List<ImageView> = listOf(
            itemView.findViewById(R.id.iv_star_1),
            itemView.findViewById(R.id.iv_star_2),
            itemView.findViewById(R.id.iv_star_3),
            itemView.findViewById(R.id.iv_star_4),
            itemView.findViewById(R.id.iv_star_5)
        )

        @SuppressLint("SetTextI18n")
        fun onBind(movie: Movie, onClickCard: (item: Movie) -> Unit) {
            Glide.with(context)
                .load(movie.imageUrl)
                .into(imageMovie)
            pg.text = movie.pgAge.toString() + "+"
            name.text = movie.title
            review.text = movie.reviewCount.toString() + " reviews"
            duration.text = movie.runningTime.toString() + " min"
            genre.text = movie.genres.joinToString { it.name }
            stars.forEachIndexed { index, star ->
                val colorId = if (movie.rating - 1 > index) R.color.pink else R.color.gray
                ImageViewCompat.setImageTintList(
                    star, ColorStateList.valueOf(
                        ContextCompat.getColor(star.context, colorId)
                    )
                )
            }
            itemView.setOnClickListener {
                onClickCard(movie)
            }
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}