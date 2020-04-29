package com.krikun.data.network

import android.content.Context
import com.krikun.data.network.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object MainApi {

    private const val CONNECTION_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 60L

    private lateinit var retrofit: Retrofit

    lateinit var mainApiService: MainApiService

    fun init(context: Context) {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor {
                // Request customization: add request headers
                it.proceed(
                    it.request().newBuilder()
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(NetworkConnectionInterceptor(context))

        retrofit = Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .baseUrl(ApiConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mainApiService = retrofit.create(MainApiService::class.java)
    }
}