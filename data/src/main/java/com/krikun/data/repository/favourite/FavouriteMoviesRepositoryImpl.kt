package com.krikun.data.repository.favourite

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.krikun.data.datasource.movie.FavouriteMovieDataBaseDataSource
import com.krikun.data.datasource.movie.MovieDataBaseDataSource
import com.krikun.data.repository.BaseRepositoryImpl
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.MoviesRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.data.common.extension.applyIoScheduler

class FavouriteMoviesRepositoryImpl(
    private val databaseSource: FavouriteMovieDataBaseDataSource
) : BaseRepositoryImpl<Entity.Movie>(), MoviesRepository {

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
                    ResultState.Loading(d) as ResultState<PagedList<Entity.Movie>>
            }
            .onErrorReturn { e -> ResultState.Error(e, null) }
    }

    override fun deleteMovie(movie: Entity.Movie): Single<ResultState<Int>> =
        databaseSource.deleteMovies(movie).map {
            ResultState.Success(it) as ResultState<Int>
        }.onErrorReturn {
            ResultState.Error(it, null)
        }


    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}