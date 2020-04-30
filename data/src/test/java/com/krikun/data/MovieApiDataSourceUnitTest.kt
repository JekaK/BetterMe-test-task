package com.krikun.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.krikun.data.extensions.fromJson
import com.krikun.data.network.ApiConfig
import com.krikun.data.network.MainApiService
import com.krikun.data.response.SampleResponse
import com.krikun.data.util.getCurrentTimeInServerFormat
import com.krikun.data.util.getTwoWeeksTimeInServerFormat
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.*


@RunWith(MockitoJUnitRunner.Silent::class)
class MovieApiDataSourceUnitTest {

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: MainApiService

    @Before
    fun setUp() {
        val okHttpClientBuilder = OkHttpClient.Builder()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(MainApiService::class.java)
    }

    @Test
    fun `test call to server`() {
        val date = Date()

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(SampleResponse.sampleResponse)

        mockWebServer.enqueue(response)

        val product = apiService.getLastRelatedMovies(
            apiKey = ApiConfig.API_KEY,
            page = 1,
            releaseDateFrom = date.getTwoWeeksTimeInServerFormat(),
            releaseDateTo = date.getCurrentTimeInServerFormat()
        ).blockingGet()

        Assert.assertNotNull(product)

        val result =
            Gson().fromJson<MainApiService.MovieDto.CommonMovieResponse<MainApiService.MovieDto.Movie>>(
            SampleResponse.sampleResponse)
        Assert.assertEquals(result, product)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}