package com.krikun.cleanarch.presentation.view.favourite

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.krikun.cleanarch.R
import com.krikun.cleanarch.presentation.base.livedata.OperationLiveData
import com.krikun.cleanarch.presentation.base.viewmodel.BaseViewModel
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCase
import io.reactivex.disposables.Disposable

class FavouriteViewModel(val moviesUseCase: GetFavouriteMoviesUseCase) : BaseViewModel() {

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
                    postValue((resultState))
                }
                tempDispossable?.track()
            }
        }

    fun getMovies() {
        fetch.postValue(true)
    }

    @SuppressLint("CheckResult")
    fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit) {
        moviesUseCase.deleteMovie(movie, deleteDone)
    }
}