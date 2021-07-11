package com.meinvodafone.testflow.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meinvodafone.testflow.R
import com.meinvodafone.testflow.data.models.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val movies: List<Movie>, private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movie = movies[position]
        holder.itemView.apply {
            Glide.with(context).load(movie.mediaList?.get(0)?.mediaUrl?.get(2)?.url).into(ivArticle)
            tvArticleTitle.text = movie.title
            tvArticleDescription.text = movie.details
            tvDate.text = movie.publishDate
            setOnClickListener { onItemClick.invoke(movie) }
        }
    }

    override fun getItemCount() = movies.size
}
