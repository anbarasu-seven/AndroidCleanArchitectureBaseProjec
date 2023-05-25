package com.example.mvvmhilt.data.api.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.room.Database
import com.example.mvvmhilt.data.room.SampleDao
import com.google.common.truth.Truth.*
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
class SampleDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // for blocking function test
    private lateinit var sampleDao: SampleDao
    private lateinit var database: Database

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java,
        ).allowMainThreadQueries().build()
        sampleDao = database.getDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun test_insert_function_in_SampleDao() = runBlockingTest {
        val item1 = UserData("name1", "craft1")

        val list = arrayListOf(item1, item1)
        sampleDao.insert(list)

        val result = sampleDao.getUsers().getOrAwaitValue()
        assertThat(result).contains(item1)
    }

    @Test
    fun test_getOrderedNetworkDataFlow_function_in_SampleDao() = runBlockingTest {
        val item1 = UserData("name1", "craft1")
        val item2 = UserData("name2", "craft2")
        val item3 = UserData("name3", "craft3")

        val list = arrayListOf(item1, item2, item3)
        sampleDao.insert(list)

        val result = sampleDao.getUsers().getOrAwaitValue()
        assertThat(result).containsExactly(item1, item2, item3)
    }

    @Test
    fun test_clearAll_function_in_SampleDao() = runBlockingTest {
        val item1 = UserData("name1", "craft1")
        val list = arrayListOf(item1)
        sampleDao.insert(list)
        sampleDao.clear()

        val result = sampleDao.getUsers().getOrAwaitValue()
        assertThat(result).doesNotContain(item1)
    }
}