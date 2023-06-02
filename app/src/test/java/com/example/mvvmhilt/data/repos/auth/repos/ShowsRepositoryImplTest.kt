package com.example.mvvmhilt.data.repos.auth.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.repos.tvshow.ShowsRepositoryImpl
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRemoteDatasource
import com.example.mvvmhilt.data.repos.tvshow.datasource.TvShowRoomDataSource
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
class ShowsRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var tvShowRemoteDatasource: TvShowRemoteDatasource

    @Mock
    lateinit var tvShowLocalDataSource: TvShowRoomDataSource

    private lateinit var showsRepositoryImpl: ShowsRepositoryImpl

    @Before
    fun setup() {
        showsRepositoryImpl = ShowsRepositoryImpl(tvShowRemoteDatasource, tvShowLocalDataSource)
    }

    @Test
    fun `make sure calling get tv shows make api call once`() {
        runBlocking {
            Mockito.`when`(tvShowRemoteDatasource.getTvShows())
                .thenReturn(UiState.Success(TvShowList(listOf())))
            showsRepositoryImpl.getTvShowsFromAPI()
            Mockito.verify(tvShowRemoteDatasource, Mockito.times(1)).getTvShows()
        }

    }

    @Test
    fun `make sure calling get tv shows make api call once and return tv shows list`() {
        runBlocking {
            val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
            val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")
            val list = listOf(item1, item2)

            Mockito.`when`(tvShowRemoteDatasource.getTvShows())
                .thenReturn(UiState.Success(TvShowList(list)))
            val result = showsRepositoryImpl.getTvShowsFromAPI()
            Truth.assertThat(result.data?.tvShows).isEqualTo(list)
        }
    }

    @Test
    fun `make sure calling get tv shows make api call once and save retrieved data db once`() {
        runBlocking {
            Mockito.`when`(tvShowRemoteDatasource.getTvShows())
                .thenReturn(UiState.Success(TvShowList(listOf())))
            showsRepositoryImpl.getTvShowsFromAPI()
            Mockito.verify(tvShowLocalDataSource, Mockito.times(1)).saveTvShowsToDB(listOf())
        }
    }
}