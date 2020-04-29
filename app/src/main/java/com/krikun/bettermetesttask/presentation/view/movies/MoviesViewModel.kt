package com.krikun.bettermetesttask.presentation.view.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.krikun.bettermetesttask.presentation.base.livedata.OperationLiveData
import com.krikun.bettermetesttask.presentation.base.viewmodel.BaseViewModel
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.movies.GetMoviesUseCase
import io.reactivex.disposables.Disposable

class MoviesViewModel(val moviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    private val fetch = MutableLiveData<String>()
    private var tempDispossable: Disposable? = null

    val moviesLiveData: LiveData<ResultState<PagedList<Entity.Movie>>> =
        Transformations.switchMap(fetch) {
            OperationLiveData<ResultState<PagedList<Entity.Movie>>> {

                if (tempDispossable?.isDisposed != true)
                    tempDispossable?.dispose()
                tempDispossable = moviesUseCase.getMovies().subscribe { resultState ->
                    postValue((resultState))
                }
                tempDispossable?.track()
            }
        }

    fun getMovies() {
        fetch.postValue("")
    }

    fun addMovieToFav(movie:Entity.Movie,insertionDone:()->Unit){
        moviesUseCase.addMovieToFavourites(movie,insertionDone)
    }
}