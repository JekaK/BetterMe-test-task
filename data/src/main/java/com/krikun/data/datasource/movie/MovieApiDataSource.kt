package com.krikun.data.datasource.movie

import com.krikun.data.datasource.BaseDataSource
import com.krikun.domain.entity.Entity
import io.reactivex.Single


interface MovieApiDataSource : BaseDataSource {

    /**
     * Get all of movies from network
     */
    suspend fun getMovies(
        releaseDateFrom: String,
        releaseDateTo: String
    ): Entity.CommonMovieResponse<Entity.Movie>?
}