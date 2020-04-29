package com.krikun.bettermetesttask.presentation.view.favourite

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.presentation.App.Companion.context
import com.krikun.bettermetesttask.presentation.base.livedata.OperationLiveData
import com.krikun.bettermetesttask.presentation.base.viewmodel.BaseViewModel
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCase
import io.reactivex.disposables.Disposable
import ir.hosseinabbasi.data.common.extension.applyIoScheduler

class FavouriteViewModel(val moviesUseCase: GetFavouriteMoviesUseCase) : BaseViewModel() {

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

    @SuppressLint("CheckResult")
    fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit) {
        moviesUseCase.deleteMovie(movie, deleteDone)
    }
}