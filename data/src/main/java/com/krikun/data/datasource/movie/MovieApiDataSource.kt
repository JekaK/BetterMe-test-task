package com.krikun.data.datasource.movie

import android.annotation.SuppressLint
import com.krikun.data.datasource.BaseDataSource
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import io.reactivex.Single


@SuppressLint("CheckResult")
fun getMovies(
    apiSource: MovieApiDataSource,
    page: Int,
    releaseDateFrom: String,
    releaseDateTo: String,
    onResult: (result: ResultState<List<Entity.Movie>>) -> Unit
) {
    apiSource.getMovies(page, releaseDateFrom, releaseDateTo)
        ?.subscribe({ data ->
            onResult(ResultState.Success(data.results))
        }, { throwable ->
            onResult(ResultState.Error(throwable, null))
        })
}

interface MovieApiDataSource : BaseDataSource {

    /**
     * Get all of movies from network
     */
    fun getMovies(
        page: Int,
        releaseDateFrom: String,
        releaseDateTo: String
    ): Single<Entity.CommonMovieResponse<Entity.Movie>>?
}