package com.krikun.domain.usecase.movies

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.usecase.BaseUseCase
import io.reactivex.Flowable
import io.reactivex.Single

interface GetFavouriteMoviesUseCase : BaseUseCase {
    fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>>

    fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit)
}