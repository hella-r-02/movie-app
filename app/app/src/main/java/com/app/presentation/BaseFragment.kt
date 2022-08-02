package com.app.presentation

import androidx.fragment.app.Fragment
import com.app.dI.ImageUrlAppender
import com.app.dI.NetworkModule
import com.app.dI.RetrofitDataSourceImpl

open class BaseFragment : Fragment() {
    private val networkModule = NetworkModule()
    val retrofitDataSourceImpl = RetrofitDataSourceImpl(
        networkModule.movieApi,
        networkModule.genreApi,
        networkModule.actorApi,
        ImageUrlAppender(networkModule.configApi)
    )
}