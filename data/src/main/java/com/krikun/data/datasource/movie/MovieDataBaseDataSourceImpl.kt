package com.krikun.data.datasource.movie

import androidx.paging.DataSource
import com.krikun.data.db.movie.MoviesDao
import com.krikun.data.mapper.map
import com.krikun.data.mapper.mapToMovie
import com.krikun.domain.entity.Entity
import io.reactivex.Single
import java.util.concurrent.Executor

class MovieDataBaseDataSourceImpl(
    private val moviesDao: MoviesDao,
    private val ioExecutor: Executor
) : MovieDataBaseDataSource {

    override fun getMovies(): DataSource.Factory<Int, Entity.Movie> = moviesDao.selectAll().map {
        it.map()
    }

    override fun persist(movies: List<Entity.Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            moviesDao.insert(movies.map {
                it.mapToMovie()
            })
            insertFinished()
        }
    }

    override fun deleteMovies(movie: Entity.Movie): Single<Int> =
        Single.just(moviesDao.delete(movie.mapToMovie()))

}