package com.example.mvvmhilt.data.api.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.room.Database
import com.example.mvvmhilt.data.room.TvShowDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest //unittest, @MediumTest - Instrumentation Test, @Large - UI test.
class TvShowsDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // for blocking function test
    private lateinit var tvShowDao: TvShowDao
    private lateinit var database: Database

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java,
        ).allowMainThreadQueries().build()
        tvShowDao = database.getTvShowDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun test_insert_operation_for_tv_shows_dao() = runBlockingTest {
        val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
        val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")

        val list = arrayListOf(item1, item2)
        tvShowDao.saveTvShows(list)

        val result = tvShowDao.getTvShows()
        assertThat(result).hasSize(2)
    }

    @Test
    fun test_get_tv_shows_from_shows_dao() = runBlockingTest {
        val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
        val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")

        val list = arrayListOf(item1, item2)
        tvShowDao.saveTvShows(list)

        val result = tvShowDao.getTvShows()
        assertThat(result).containsExactly(item1, item2)
    }

    @Test
    fun test_clearAll_from_shows_dao() = runBlockingTest {
        val item1 = TvShow("name1", 1, name = "Name", overview = "overview", posterPath = "//path")
        val item2 = TvShow("name2", 2, name = "Name", overview = "overview", posterPath = "//path")

        val list = arrayListOf(item1, item2)
        tvShowDao.saveTvShows(list)

        tvShowDao.deleteAllTvShows()
        val result = tvShowDao.getTvShows()
        assertThat(result).isEmpty()
    }
}