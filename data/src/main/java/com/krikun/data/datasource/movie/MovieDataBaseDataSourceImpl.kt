package com.krikun.data.datasource.movie

import com.krikun.data.db.movie.FavouriteMoviesDao
import com.krikun.data.mapper.map
import com.krikun.domain.entity.Entity
import io.reactivex.Single
import java.util.concurrent.Executor

class MovieDataBaseDataSourceImpl(
    private val moviesDao: FavouriteMoviesDao,
    private val ioExecutor: Executor
) : MovieDataBaseDataSource {
    override fun getMovies(): List<Entity.Movie> = moviesDao.selectAll().map {
        it.map()
    }

    override fun persist(movies: List<Entity.Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            moviesDao.insert(movies.map {
                it.map()
            })
            insertFinished()
        }
    }

    override fun deleteMovies(movie: Entity.Movie): Single<Int> = moviesDao.delete(movie.map())

}