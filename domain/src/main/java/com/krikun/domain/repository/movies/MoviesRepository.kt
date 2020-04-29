package com.krikun.domain.repository.movies

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.BaseRepository
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository : BaseRepository {
    fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>>

    fun deleteMovie(movie: Entity.Movie): Single<ResultState<Int>>

}