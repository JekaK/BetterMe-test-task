package com.krikun.domain.repository.movies

import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import io.reactivex.Single

interface FavouriteMovieRepository : MoviesRepository {
    fun addMovieToFavourite(movie: Entity.Movie,insertDone:()->Unit)
}