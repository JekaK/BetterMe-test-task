package com.krikun.domain.usecase.movies

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import com.krikun.domain.repository.movies.MoviesRepository
import com.krikun.domain.transformer.STransformer
import io.reactivex.Flowable
import io.reactivex.Observable

class GetMoviesUseCaseImpl(
    private val transformerSingle: STransformer<ResultState<Int>>,
    private val repository: MoviesRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) : GetMoviesUseCase {
    override fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>> =
        repository.getMovies()

    override fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit) {
        repository.deleteMovie(movie, deleteDone)
    }

    override fun addMovieToFavourites(movie: Entity.Movie, insertionDone: () -> Unit) {
        favouriteMovieRepository.addMovieToFavourite(movie, insertionDone)
    }

    override fun getLoadingStateWatcher(): Observable<String> =
        repository.getLoadingStateWatcher()
}

