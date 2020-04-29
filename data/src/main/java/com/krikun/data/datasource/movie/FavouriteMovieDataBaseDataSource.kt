package com.krikun.data.datasource.movie

import androidx.paging.DataSource
import com.krikun.domain.entity.Entity
import io.reactivex.Single

interface FavouriteMovieDataBaseDataSource {

    fun getMovies(): DataSource.Factory<Int, Entity.Movie>

    fun persist(movies: List<Entity.Movie>, insertFinished: () -> Unit)

    fun deleteMovies(movie: Entity.Movie): Single<Int>
}