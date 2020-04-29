package com.krikun.domain.usecase.movies

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import com.krikun.domain.repository.movies.MoviesRepository
import com.krikun.domain.transformer.STransformer
import io.reactivex.Flowable
import io.reactivex.Single

class GetFavouriteMoviesUseCaseImpl(
    private val transformerSingle: STransformer<ResultState<Int>>,
    private val favouriteMovieRepository: FavouriteMovieRepository
) : GetFavouriteMoviesUseCase {

    override fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>> =
        favouriteMovieRepository.getMovies()

    override fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit) {
        favouriteMovieRepository.deleteMovie(movie, deleteDone)
    }
}