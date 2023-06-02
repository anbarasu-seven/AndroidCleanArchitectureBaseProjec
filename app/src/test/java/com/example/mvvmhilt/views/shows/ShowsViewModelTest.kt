package com.example.mvvmhilt.views.shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.common.getOrAwaitValueTest
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
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

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ShowsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getTvShowsUseCase: GetShowsUseCase

    lateinit var showsViewModel: ShowsViewModel

    @Before
    fun setup() {
        showsViewModel = ShowsViewModel(getTvShowsUseCase)
    }

    @Test
    fun `get tv shows function in ShowsViewModel should post LOADING state initially`() {
        runBlocking {
            Mockito.`when`(getTvShowsUseCase.execute()).thenReturn(UiState.Loading())
            showsViewModel.getTvShows()
            val result = showsViewModel.tvShows.getOrAwaitValueTest()
            Truth.assertThat(result).isInstanceOf(UiState.Loading::class.java)
        }
    }

    @Test
    fun `get tv shows function in ShowsViewModel should return SUCCESS state`() {
        runBlocking {
            val testData = TvShowList(listOf())
            Mockito.`when`(getTvShowsUseCase.execute()).thenReturn(UiState.Success(testData))
            showsViewModel.getTvShows()
            val result = showsViewModel.tvShows.getOrAwaitValueTest()
            Truth.assertThat(result).isInstanceOf(UiState.Success::class.java)
        }
    }

    @Test
    fun `get tv shows function in ShowsViewModel should return SUCCESS state with empty tv shows list`() {
        runBlocking {
            val testData = TvShowList(listOf())
            Mockito.`when`(getTvShowsUseCase.execute()).thenReturn(UiState.Success(testData))
            showsViewModel.getTvShows()
            val result = showsViewModel.tvShows.getOrAwaitValueTest()
            Truth.assertThat(result?.data?.tvShows).isEmpty()
        }
    }

    @Test
    fun `get tv shows function in ShowsViewModel should return SUCCESS state with valid tv shows list`() {
        runBlocking {
            val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
            val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")
            val list = listOf(item1, item2)
            val testData = TvShowList(list)
            Mockito.`when`(getTvShowsUseCase.execute()).thenReturn(UiState.Success(testData))
            showsViewModel.getTvShows()
            val result = showsViewModel.tvShows.getOrAwaitValueTest()
            Truth.assertThat(result?.data?.tvShows).containsExactly(item1, item2)
        }
    }

}