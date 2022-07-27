package com.app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast

class FragmentMoviesList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var itemClickListener: MoviesListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesListItemClickListener) {
            itemClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val movieCard = view.findViewById<FrameLayout>(R.id.movie_card_list)
        movieCard.setOnClickListener {
            itemClickListener?.onMovieSelected()
        }
        return view
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected()
    }

}