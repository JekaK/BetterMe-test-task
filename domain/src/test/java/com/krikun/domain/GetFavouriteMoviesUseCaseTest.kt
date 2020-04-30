package com.krikun.domain

import androidx.paging.PagedList
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.DummyEntity
import com.krikun.domain.entity.Entity
import com.krikun.domain.repository.movies.FavouriteMovieRepository
import com.krikun.domain.transformer.STransformer
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCase
import com.krikun.domain.usecase.movies.GetFavouriteMoviesUseCaseImpl
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class GetFavouriteMoviesUseCaseTest {

    private lateinit var getMovieUseCase: GetFavouriteMoviesUseCase

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
        getMovieUseCase = GetFavouriteMoviesUseCaseImpl(transformer, favMoviesRepository)
        pagedList = Mockito.mock(PagedList::class.java) as PagedList<Entity.Movie>
    }

    @Test
    fun `invoke should return list of movies`() {
        val expectedResult = Flowable.just(
            ResultState.Success(
                pagedList
            )
        )
        whenever(favMoviesRepository.getMovies())
            .thenReturn(
                expectedResult as Flowable<ResultState<PagedList<Entity.Movie>>>
            )

        val result = getMovieUseCase.getMovies()
        verify(favMoviesRepository).getMovies()

        Mockito.verifyNoMoreInteractions(favMoviesRepository)

        Assert.assertNotNull(result)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `invoke should call callback method when deleteMovie invoked`() {
        getMovieUseCase.deleteMovie(dummyEntityMovie, dummyCallback)
        verify(favMoviesRepository, only()).deleteMovie(dummyEntityMovie, dummyCallback)

        Mockito.verifyNoMoreInteractions(favMoviesRepository)
        Mockito.verifyNoMoreInteractions(favMoviesRepository)
    }
}