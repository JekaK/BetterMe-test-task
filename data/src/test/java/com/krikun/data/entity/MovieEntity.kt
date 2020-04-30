package com.krikun.data.entity

import com.krikun.data.db.movie.MovieData
import com.krikun.data.network.MainApiService
import com.krikun.domain.entity.Entity

object DummyEntity {
    val movieData = MovieData.Movie(
        true,
        "",
        listOf(),
        0,
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        true,
        0.0,
        1
    )

    val enitity = Entity.Movie(
        true,
        "",
        listOf(),
        0,
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        true,
        0.0,
        1
    )

    val dtoMovie = MainApiService.MovieDto.Movie(
        true,
        "",
        listOf(),
        0,
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        true,
        0.0,
        1
    )

}