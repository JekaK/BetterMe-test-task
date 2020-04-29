package com.krikun.data.repository.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.krikun.data.datasource.movie.MovieApiDataSource
import com.krikun.data.datasource.movie.MovieDataBaseDataSource
import com.krikun.data.datasource.movie.getMovies
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity

class RepoBoundaryCallback(
    private val apiSource: MovieApiDataSource,
    private val databaseSource: MovieDataBaseDataSource,
    private val releaseDateFrom: String,
    private val releaseDateTo: String
) : PagedList.BoundaryCallback<Entity.Movie>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 0
    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should send request to the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to send a request to the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Entity.Movie) {
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
                    _networkErrors.postValue(movies.throwable.message)
                    isRequestInProgress = false
                }
            }
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}