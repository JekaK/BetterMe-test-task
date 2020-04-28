package com.krikun.data.datasource.movie

import com.krikun.data.extensions.castError
import com.krikun.data.mapper.map
import com.krikun.data.network.MainApi
import com.krikun.data.network.MainApiService
import com.krikun.domain.entity.Entity
import kotlinx.coroutines.coroutineScope

class MoviesApiDataSourceImpl(private val api: MainApiService) : MovieApiDataSource {

    override suspend fun getMovies(
        releaseDateFrom: String,
        releaseDateTo: String
    ): Entity.CommonMovieResponse<Entity.Movie>? {
        val response = coroutineScope {
            MainApi.mainApiService.getLastRelatedMovies(
                releaseDateFrom = releaseDateFrom,
                releaseDateTo = releaseDateTo
            )
        }.await()

        return if (response.isSuccessful) {
            response.body()?.map()
        } else {
            response.castError()
        }
    }
}