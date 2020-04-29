package com.krikun.data.network

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {
    @GET("discover/movie")
    fun getLastRelatedMovies(
        @Query("api_key") apiKey: String = ApiConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("release_date.gte") releaseDateFrom: String,
        @Query("release_date.lte") releaseDateTo: String

    ): Single<MovieDto.CommonMovieResponse<MovieDto.Movie>>

    sealed class MovieDto {

        data class CommonMovieResponse<T>(
            val page: Int,
            val results: List<T>,
            val total_pages: Int,
            val total_results: Int
        ) : MovieDto()

        data class Movie(
            val adult: Boolean,
            val backdrop_path: String,
            val genre_ids: List<Int>,
            val id: Long,
            val original_language: String,
            val original_title: String,
            val overview: String,
            val popularity: Double,
            val poster_path: String,
            val release_date: String,
            val title: String,
            val video: Boolean,
            val vote_average: Double,
            val vote_count: Int
        ) : MovieDto()
    }
}