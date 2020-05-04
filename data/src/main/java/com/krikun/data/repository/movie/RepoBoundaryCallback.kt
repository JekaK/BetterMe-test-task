package com.krikun.data.repository.movie

import androidx.paging.PagedList
import com.krikun.data.datasource.movie.MovieApiDataSource
import com.krikun.data.datasource.movie.MovieDataBaseDataSource
import com.krikun.data.datasource.movie.getMovies
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import io.reactivex.subjects.BehaviorSubject

class RepoBoundaryCallback(
    private val apiSource: MovieApiDataSource,
    private val databaseSource: MovieDataBaseDataSource,
    private val loadingState: BehaviorSubject<String>,
    private val releaseDateFrom: String,
    private val releaseDateTo: String
) : PagedList.BoundaryCallback<Entity.Movie>() {

    private var lastRequestedPage = 1

    var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

//    override fun onItemAtEndLoaded(itemAtEnd: Entity.Movie) {
//        requestAndSaveData()
//    }

    override fun onItemAtFrontLoaded(itemAtFront: Entity.Movie) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getMovies(
            apiSource, lastRequestedPage,
            releaseDateFrom = releaseDateFrom,
            releaseDateTo = releaseDateTo
        ) { movies ->
            when (movies) {
                is ResultState.Success -> {
                    databaseSource.persist(movies.data) {
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                }
                is ResultState.Error -> {
                    movies.throwable.message?.let { loadingState.onNext(it) }
                    isRequestInProgress = false
                }
            }
        }
    }
}