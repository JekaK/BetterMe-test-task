package com.krikun.bettermetesttask.presentation.view.movies

import androidx.lifecycle.MutableLiveData
import com.krikun.bettermetesttask.presentation.base.viewmodel.BaseViewModel
import com.krikun.domain.usecase.movies.GetMoviesUseCase

class MoviesViewModel(moviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    private val fetch = MutableLiveData<String>()


}