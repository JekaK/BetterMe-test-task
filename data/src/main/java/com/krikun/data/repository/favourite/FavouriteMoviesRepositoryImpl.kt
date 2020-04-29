package com.krikun.data.repository.favourite

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.krikun.data.datasource.movie.FavouriteMovieDataBaseDataSource
import com.krikun.data.repository.BaseRepositoryImpl
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import ir.hosseinabbasi.data.common.extension.applyIoScheduler

class FavouriteMoviesRepositoryImpl(
    private val databaseSource: FavouriteMovieDataBaseDataSource
) : BaseRepositoryImpl<Entity.Movie>(), FavouriteMovieRepository {

    override fun addMovieToFavourite(movie: Entity.Movie, insertDone: () -> Unit) {
        databaseSource.persist(listOf(movie), insertDone)
    }

    override fun getMovies(): Flowable<ResultState<PagedList<Entity.Movie>>> {
        val dataSourceFactory = databaseSource.getMovies()

        val data = RxPagedListBuilder(
            dataSourceFactory,
            DATABASE_PAGE_SIZE
        ).buildFlowable(BackpressureStrategy.BUFFER)

        return data
            .applyIoScheduler()
            .map { d ->
                if (d.size > 0)
                    ResultState.Success(d) as ResultState<PagedList<Entity.Movie>>
                else
                    ResultState.Empty<Entity.Movie>() as ResultState<PagedList<Entity.Movie>>
            }
            .onErrorReturn { e -> ResultState.Error(e, null) }
    }

    override fun deleteMovie(movie: Entity.Movie, deleteDone: () -> Unit) {
        databaseSource.deleteMovies(movie, deleteDone)
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}