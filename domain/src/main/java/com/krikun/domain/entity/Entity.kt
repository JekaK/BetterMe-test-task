package com.krikun.domain.entity

sealed class Entity {

    data class Movie(
        val adult: Boolean?,
        val backdrop_path: String?,
        val genre_ids: List<Int>?,
        val id: Long?,
        val original_language: String?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val release_date: String?,
        var title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?
    ) : Entity()

    data class CommonMovieResponse<T>(
        var page: Int,
        var results: List<T>,
        var total_pages: Int,
        var total_results: Int
    ) : Entity()
}