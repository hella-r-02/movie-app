package com.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class FragmentMoviesList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val movieCard = view.findViewById<FrameLayout>(R.id.movie_card_list)
        movieCard.setOnClickListener {
            parentFragmentManager.apply {
                beginTransaction()
                    .apply {
                        replace(R.id.frameLayoutContainer, FragmentMoviesDetails())
                        addToBackStack(FragmentMoviesDetails::class.java.simpleName)
                        commit()
                    }
            }
        }
        return view
    }
}