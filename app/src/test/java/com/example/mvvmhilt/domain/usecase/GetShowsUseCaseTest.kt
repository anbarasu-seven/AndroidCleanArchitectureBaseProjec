package com.example.mvvmhilt.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.data.models.DataState
import com.example.mvvmhilt.data.repos.tvshow.ShowsRepo
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetShowsUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var tvShowRepository: ShowsRepo

    private lateinit var getShowsUseCase: GetShowsUseCase

    @Before
    fun setup() {
        getShowsUseCase = GetShowsUseCase(tvShowRepository)
    }

    @Test
    fun `executing getTvShowUsecase should return LOADING state initially`() {
        runBlocking {
            Mockito.`when`(tvShowRepository.getTvShows()).thenReturn(DataState.Loading())
            val result = getShowsUseCase.execute()
            Truth.assertThat(result).isInstanceOf(DataState.Loading::class.java)
        }
    }

    @Test
    fun `executing getTvShowUsecase should return SUCCESS state`() {
        runBlocking {
            val testData = TvShowList(listOf())
            Mockito.`when`(tvShowRepository.getTvShows()).thenReturn(DataState.Success(testData))
            val result = getShowsUseCase.execute()
            Truth.assertThat(result).isInstanceOf(DataState.Success::class.java)
        }
    }

    @Test
    fun `executing getTvShowUsecase should return SUCCESS state with empty tv shows list`() {
        runBlocking {
            val testData = TvShowList(listOf())
            Mockito.`when`(tvShowRepository.getTvShows()).thenReturn(DataState.Success(testData))
            val result = getShowsUseCase.execute()
            Truth.assertThat(result?.data?.tvShows).isEmpty()
        }
    }

    @Test
    fun `executing getTvShowUsecase should return SUCCESS state with valid tv shows list`() {
        runBlocking {
            val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
            val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")
            val list = listOf(item1, item2)
            val testData = TvShowList(list)
            Mockito.`when`(tvShowRepository.getTvShows()).thenReturn(DataState.Success(testData))
            val result = getShowsUseCase.execute()
            Truth.assertThat(result?.data?.tvShows).containsExactly(item1, item2)
        }
    }
}