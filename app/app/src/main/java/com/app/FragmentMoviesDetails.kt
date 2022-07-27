package com.app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class FragmentMoviesDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var onBackClickListener: MoviesDetailsButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesDetailsButtonClickListener) {
            onBackClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        val backButton = view.findViewById<LinearLayout>(R.id.ll_back_button)
        backButton.setOnClickListener {
            onBackClickListener?.onClickBack()
        }
        return view
    }

    interface MoviesDetailsButtonClickListener {
        fun onClickBack()
    }
}