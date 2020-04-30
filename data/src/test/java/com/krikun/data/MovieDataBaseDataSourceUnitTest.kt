package com.krikun.data

import androidx.room.Room
import com.krikun.data.datasource.movie.MovieDataBaseDataSourceImpl
import com.krikun.data.db.MainDb
import com.krikun.data.db.movie.MovieData
import com.krikun.data.db.movie.MoviesDao
import com.krikun.data.entity.DummyEntity
import com.krikun.data.extensions.createMockDataSourceFactory
import com.krikun.domain.entity.Entity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors


@RunWith(MockitoJUnitRunner.Silent::class)
class MovieDataBaseDataSourceUnitTest {

    @Mock
    private lateinit var movieDao: MoviesDao

    @Rule
    @JvmField
    var expectedException = ExpectedException.none()

    private lateinit var movieDataBaseDataSourceImpl: MovieDataBaseDataSourceImpl

    private val dummyCollection = ArrayList<MovieData.Movie>()
    private val dummyCollectionToInsert = ArrayList<Entity.Movie>()

    private fun fillCollection() {
        for (i in 1..3) {
            dummyCollection.add(DummyEntity.movieData)
            dummyCollectionToInsert.add(DummyEntity.enitity)
        }
    }

    @Before
    fun setUp() {
        fillCollection()
        movieDataBaseDataSourceImpl = MovieDataBaseDataSourceImpl(
            movieDao, Executors.newSingleThreadExecutor()
        )
    }

    @Test
    fun `insert list result after calling insert()`() {
        val collectionSize = ArrayList(dummyCollectionToInsert)

        whenever(movieDao.insert(dummyCollection))
            .then {
                dummyCollectionToInsert.addAll(dummyCollectionToInsert)
            }

        movieDataBaseDataSourceImpl.persist(dummyCollectionToInsert) {
        }
        Mockito.verifyNoMoreInteractions(movieDao)
        Thread.sleep(100)
        Assert.assertSame(collectionSize.size * 2, dummyCollectionToInsert.size)
    }

    @Test
    fun `select list result after calling getMovies()`() {
        val expectedResult = createMockDataSourceFactory(dummyCollection)

        whenever(movieDao.selectAll())
            .thenReturn(
                expectedResult
            )

        val result = movieDataBaseDataSourceImpl.getMovies()

        verify(movieDao).selectAll()
        Assert.assertNotNull(result)
    }

}