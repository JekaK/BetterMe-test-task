package com.krikun.data.datasource.movie

import com.krikun.domain.entity.Entity
import io.reactivex.Single

interface MovieDataBaseDataSource {

    fun getMovies(): List<Entity.Movie>

    fun persist(movies: List<Entity.Movie>, insertFinished: () -> Unit)

    fun deleteMovies(movie: Entity.Movie): Single<Int>
}