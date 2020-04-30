package com.krikun.domain

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.DummyEntity
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import com.krikun.domain.repository.movies.MoviesRepository
import com.krikun.domain.transformer.STransformer
import com.krikun.domain.usecase.movies.GetMoviesUseCaseImpl
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert;
import org.junit.Rule
import org.junit.rules.ExpectedException

@RunWith(MockitoJUnitRunner.Silent::class)
class GetMoviesUseCaseUnitTest {

    private lateinit var getMovieUseCase: GetMoviesUseCaseImpl

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var favMoviesRepository: FavouriteMovieRepository

    @Mock
    private lateinit var transformer: STransformer<ResultState<Int>>

    @Rule
    @JvmField
    var expectedException = ExpectedException.none()

    private lateinit var pagedList: PagedList<Entity.Movie>

    private val dummyEntityMovie = DummyEntity.enitity

    private val dummyCallback = {

    }

    @Before
    fun setUp() {
        getMovieUseCase = GetMoviesUseCaseImpl(transformer, moviesRepository, favMoviesRepository)
        pagedList = Mockito.mock(PagedList::class.java) as PagedList<Entity.Movie>
    }

    @Test
    fun `invoke should return list of movies`() {
        val expectedResult = Flowable.just(
            ResultState.Success(
                pagedList
            )
        )
        whenever(moviesRepository.getMovies())
            .thenReturn(
                expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
            )

        val result = getMovieUseCase.getMovies()
        verify(moviesRepository).getMovies()

        Mockito.verifyNoMoreInteractions(moviesRepository)
        Mockito.verifyNoMoreInteractions(favMoviesRepository)

        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke should call callback method when deleteMovie invoked`() {
        getMovieUseCase.deleteMovie(dummyEntityMovie, dummyCallback)
        verify(moviesRepository, only()).deleteMovie(dummyEntityMovie, dummyCallback)

        Mockito.verifyNoMoreInteractions(moviesRepository)
        Mockito.verifyNoMoreInteractions(favMoviesRepository)
    }

    @Test
    fun `invoke should return errorWatcherObserwable method when get invoked`() {
        val expectedResult: Observable<String> = Observable.fromCallable { "" }
        whenever(moviesRepository.getErrorWatcher())
            .thenReturn(expectedResult)

        val result = getMovieUseCase.getErrorWatcher()
        verify(moviesRepository).getErrorWatcher()

        Mockito.verifyNoMoreInteractions(moviesRepository)
        Mockito.verifyNoMoreInteractions(favMoviesRepository)

        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke should call callback method when addMovieToFavourites invoked`() {
        getMovieUseCase.addMovieToFavourites(dummyEntityMovie, dummyCallback)
        verify(favMoviesRepository, only()).addMovieToFavourite(dummyEntityMovie, dummyCallback)

        Mockito.verifyNoMoreInteractions(moviesRepository)
        Mockito.verifyNoMoreInteractions(favMoviesRepository)
    }

}