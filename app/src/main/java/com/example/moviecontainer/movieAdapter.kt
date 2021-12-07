package com.example.moviecontainer

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.collections.ArrayList



class movieAdapter(context: Context, wordList: ArrayList<Movie>, private var onNoteListener: OnMovieListener) :
    RecyclerView.Adapter<movieAdapter.MovieViewHolder>() {

    private var activity:Context = context

    private val movieList: ArrayList<Movie> = wordList;
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    interface OnMovieListener{
        fun onMovieClick(imdbId: String)

    }
    inner class MovieViewHolder(
        itemView: View,
        adapter: movieAdapter,
        onmovieListener: OnMovieListener
    ):
        RecyclerView.ViewHolder(itemView){
        val moviePoster:ImageView=itemView.findViewById(R.id.poster)
        val movieTitle:TextView = itemView.findViewById(R.id.title)
        val movieYear:TextView=itemView.findViewById(R.id.year)

        private val mAdapter: movieAdapter
        init {
            val card=itemView.findViewById<CardView>(R.id.cardView);
            mAdapter = adapter
            card.setOnClickListener{
                onmovieListener.onMovieClick(movieList[layoutPosition].imdbID)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflate an item view.
        val mItemView: View = mInflater.inflate(
            R.layout.movie_item, parent, false
        )
        return MovieViewHolder(mItemView,this,onNoteListener)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Retrieve the data for that position.
        val mCurrent = movieList[position]
        // Add the data to the view holder.
        //Read about glide for this
        holder.movieTitle.text = mCurrent.Title;
        holder.movieYear.text=mCurrent.Year

        Glide.with(activity).
        load(mCurrent.Poster).
        diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.moviePoster)

    }
    override fun getItemCount(): Int {
        return movieList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(movieLists: List<Movie>) {
        movieList.clear()
        movieList.addAll(movieLists)
        notifyDataSetChanged()
    }

}