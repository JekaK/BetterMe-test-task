package com.krikun.domain.usecase.movies

import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.BaseUseCase
import io.reactivex.Flowable
import io.reactivex.Single

interface GetMoviesUseCase : BaseUseCase {
    fun getMovies(): Flowable<ResultState<List<Entity.Movie>>>

    fun deleteAlbum(album: Entity.Movie): Single<ResultState<Int>>

    fun loadMovies(
        dateFrom: String,
        dateTo: String
    ): Single<ResultState<Entity.CommonMovieResponse<Entity.Movie>>>

}