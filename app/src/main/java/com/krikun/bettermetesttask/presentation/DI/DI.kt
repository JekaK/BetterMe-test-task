package com.krikun.bettermetesttask.presentation.DI

import com.krikun.bettermetesttask.presentation.transformer.AsyncSTransformer
import com.krikun.bettermetesttask.presentation.view.MainViewModel
import com.krikun.bettermetesttask.presentation.view.favourite.FavouriteViewModel
import com.krikun.bettermetesttask.presentation.view.movies.MoviesViewModel
import com.krikun.data.datasource.movie.*
import com.krikun.data.db.MainDb
import com.krikun.data.network.ApiConfig
import com.krikun.data.network.MainApi
import com.krikun.data.repository.favourite.FavouriteMoviesRepositoryImpl
import com.krikun.data.repository.movie.MoviesRepositoryImpl
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import com.krikun.domain.repository.movies.MoviesRepository
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCase
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCaseImpl
import com.krikun.domain.usecase.movies.GetMoviesUseCase
import com.krikun.domain.usecase.movies.GetMoviesUseCaseImpl
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.Executors

object DI : IDIHolder {
    override fun provideAppScopeModules(): List<Module> =
        listOf(appModule)

    override fun provideApiConfig() =
        DICommon.Api.Config(
            ApiConfig.BASE_URL,
            listOf(Interceptor {
                it.proceed(
                    it.request().newBuilder()
                        .build()
                )
            })
        )

    private val appModule = module {
        single {
            MainDb.buildDatabase(androidApplication())
        }
        single { get<MainDb>().moviesDao() }
        single { get<MainDb>().favouriteMoviesDao() }
        single { MainApi.mainApiService }
        single {
            FavouriteMovieDataBaseDataSourceImpl(
                get(), Executors.newSingleThreadExecutor()
            ) as FavouriteMovieDataBaseDataSource
        }
        single { MoviesApiDataSourceImpl(get()) as MovieApiDataSource }
        single {
            MovieDataBaseDataSourceImpl(
                get(),
                Executors.newSingleThreadExecutor()
            ) as MovieDataBaseDataSource
        }
        single { FavouriteMoviesRepositoryImpl(get()) as FavouriteMovieRepository }
        single { MoviesRepositoryImpl(get(), get()) as MoviesRepository }
        factory {
            GetFavouriteMoviesUseCaseImpl(
                AsyncSTransformer(),
                get()
            ) as GetFavouriteMoviesUseCase
        }
        factory { GetMoviesUseCaseImpl(AsyncSTransformer(), get(), get()) as GetMoviesUseCase }
        viewModel { MoviesViewModel(get()) }
        viewModel { FavouriteViewModel(get()) }
        viewModel { MainViewModel() }
    }
}