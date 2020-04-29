package com.krikun.data.datasource.movie

import androidx.paging.DataSource
import com.krikun.data.db.movie.FavouriteMoviesDao
import com.krikun.data.mapper.map
import com.krikun.data.mapper.mapToFavouriteMovie
import com.krikun.domain.entity.Entity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import java.util.concurrent.Executor
import java.util.function.Consumer

class FavouriteMovieDataBaseDataSourceImpl(
    private val moviesDao: FavouriteMoviesDao,
    private val ioExecutor: Executor
) : FavouriteMovieDataBaseDataSource {
    override fun getMovies(): DataSource.Factory<Int, Entity.Movie> = moviesDao.selectAll().map {
        it.map()
    }

    override fun persist(movies: List<Entity.Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            moviesDao.insert(movies.map {
                it.mapToFavouriteMovie()
            })
            insertFinished()
        }
    }

    override fun deleteMovies(movie: Entity.Movie, deleteDone: () -> Unit) {

        ioExecutor.execute {
            moviesDao.delete(movie.mapToFavouriteMovie())
            deleteDone()
        }

    }


}

