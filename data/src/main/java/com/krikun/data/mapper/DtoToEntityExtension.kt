package com.krikun.data.mapper

import com.krikun.data.network.MainApiService
import com.krikun.domain.entity.Entity

fun MainApiService.MovieDto.Movie.map() = Entity.Movie(
    adult = adult,
    backdrop_path = backdrop_path,
    genre_ids = genre_ids,
    id = id,
    original_language = original_language,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path = poster_path,
    release_date = release_date,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count
)

fun MainApiService.MovieDto.CommonMovieResponse<MainApiService.MovieDto.Movie>.map() =
    Entity.CommonMovieResponse(
        page = page,
        results = results.map{
            it.map()
        },
        total_pages = total_pages,
        total_results = total_results
    )