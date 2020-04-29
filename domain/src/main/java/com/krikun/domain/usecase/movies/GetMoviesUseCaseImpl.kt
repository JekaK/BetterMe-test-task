package com.krikun.domain.usecase.movies

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.MoviesRepository
import com.krikun.domain.transformer.STransformer
import io.reactivex.Flowable
import io.reactivex.Single

class GetMoviesUseCaseImpl(
    private val transformerSingle: STransformer<ResultState<Int>>,
    private val repository: MoviesRepository
) : GetMoviesUseCase {
    override fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>> =
        repository.getMovies()

    override fun deleteMovie(movie: Entity.Movie): Single<ResultState<Int>> =
        repository.deleteMovie(movie).compose(transformerSingle)

}