package com.krikun.bettermetesttask.presentation.view.movies

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.presentation.base.livedata.OperationLiveData
import com.krikun.bettermetesttask.presentation.base.livedata.SingleLiveEvent
import com.krikun.bettermetesttask.presentation.base.viewmodel.BaseViewModel
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.movies.GetMoviesUseCase
import io.reactivex.disposables.Disposable
import ir.hosseinabbasi.data.common.extension.applyIoScheduler

class MoviesViewModel(val moviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    private val fetch = MutableLiveData<Boolean>()
    private var tempDispossable: Disposable? = null

    var isLoading = ObservableField<Boolean>()
    var isEmpty = ObservableField<Boolean>()
    var emptyRes = ObservableField<Int>(R.drawable.empty)

    val moviesLiveData: LiveData<ResultState<PagedList<Entity.Movie>>> =
        Transformations.switchMap(fetch) {
            OperationLiveData<ResultState<PagedList<Entity.Movie>>> {

                if (tempDispossable?.isDisposed != true)
                    tempDispossable?.dispose()

                tempDispossable = moviesUseCase.getMovies().subscribe { resultState ->
                    postValue(resultState)
                }

                tempDispossable?.track()
            }
        }
    private val errorLiveData: MutableLiveData<String> = SingleLiveEvent()
    val errorData: LiveData<String> by lazy { errorLiveData }

    init {
        moviesUseCase.getErrorWatcher().apply {
            applyIoScheduler()
                .subscribe {
                    errorLiveData.postValue(it)
                }
        }
    }

    fun getMovies() {
        fetch.postValue(true)
    }

    fun addMovieToFav(movie: Entity.Movie, insertionDone: () -> Unit) {
        moviesUseCase.addMovieToFavourites(movie, insertionDone)
    }
}