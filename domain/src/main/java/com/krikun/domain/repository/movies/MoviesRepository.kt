package com.krikun.domain.repository.movies

import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.BaseRepository
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository : BaseRepository {
    fun getMovies(): Flowable<ResultState<List<Entity.Movie>>>

    fun deleteAlbum(album: Entity.Movie): Single<ResultState<Int>>

    fun loadMovies(
        dateFrom: String,
        dateTo: String
    ): Single<ResultState<Entity.CommonMovieResponse<Entity.Movie>>>
}