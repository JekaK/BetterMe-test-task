package com.krikun.data.datasource.movie

import android.annotation.SuppressLint
import com.krikun.data.mapper.map
import com.krikun.data.network.MainApiService
import com.krikun.domain.entity.Entity
import io.reactivex.Single
import ir.hosseinabbasi.data.common.extension.applyIoScheduler

class MoviesApiDataSourceImpl(private val api: MainApiService) : MovieApiDataSource {


    @SuppressLint("CheckResult")
    override fun getMovies(
        page: Int,
        releaseDateFrom: String,
        releaseDateTo: String
    ): Single<Entity.CommonMovieResponse<Entity.Movie>>? {
        return api.getLastRelatedMovies(
            page = page,
            releaseDateFrom = releaseDateFrom,
            releaseDateTo = releaseDateTo
        )
            .applyIoScheduler()
            .map { item ->
                item.map()
            }
            .doOnError {
                it.printStackTrace()
            }
    }

}