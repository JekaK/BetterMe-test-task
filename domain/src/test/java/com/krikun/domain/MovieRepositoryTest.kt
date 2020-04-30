package com.krikun.domain

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.DummyEntity
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.MoviesRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception


@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Rule
    @JvmField
    var expectedException = ExpectedException.none()


    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var pagedList: PagedList<Entity.Movie>

    private val dummyEntityMovie = DummyEntity.enitity

    private val dummyCallback = {

    }

    @Before
    fun setUp() {
        pagedList = Mockito.mock(PagedList::class.java) as PagedList<Entity.Movie>
    }

    @Test
    fun `invoke list result after calling getMovies()`() {
        val expectedResult = Flowable.just(
            ResultState.Success(
                pagedList
            )
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
        )

        val result = moviesRepository.getMovies()
        verify(moviesRepository).getMovies()
        verifyNoMoreInteractions(moviesRepository)
        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke error after calling getMovies()`() {
        val expectedResult = Flowable.just(
            ResultState.Error(
                Exception("Error"),
                pagedList
            )
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult
        )

        val result = moviesRepository.getMovies()

        verify(moviesRepository).getMovies()
        verifyNoMoreInteractions(moviesRepository)
        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke Loading after calling getMovies()`() {
        val expectedResult = Flowable.just(
            ResultState.Loading(
                pagedList
            )
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult
        )

        val result = moviesRepository.getMovies()

        verify(moviesRepository).getMovies()
        verifyNoMoreInteractions(moviesRepository)
        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke Empty after calling getMovies()`() {
        val expectedResult = Flowable.just(
            ResultState.Empty<Entity.Movie>()
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
        )

        whenever(moviesRepository.getMovies()).thenReturn(
            expectedResult
        )

        val result = moviesRepository.getMovies()

        verify(moviesRepository).getMovies()
        verifyNoMoreInteractions(moviesRepository)
        Assert.assertNotNull(result)
        Assert.assertSame(result, expectedResult)
    }

    @Test
    fun `invoke callback after calling deleteMovie()`() {

        moviesRepository.deleteMovie(dummyEntityMovie, dummyCallback)

        verify(moviesRepository).deleteMovie(dummyEntityMovie, dummyCallback)
        verifyNoMoreInteractions(moviesRepository)
    }

    @Test
    fun `invoke errorWatcher after calling getErrorWatcher()`() {
        val expectedResult: Observable<String> = Observable.fromCallable { "" }

        whenever(moviesRepository.getErrorWatcher())
            .thenReturn(expectedResult)

        val result = moviesRepository.getErrorWatcher()

        verify(moviesRepository).getErrorWatcher()
        verifyNoMoreInteractions(moviesRepository)
        Assert.assertNotNull(result)
        Assert.assertSame(result, expectedResult)
    }
}