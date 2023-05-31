package com.example.mvvmhilt.views.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.data.api.room.getOrAwaitValueTest
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.User
import com.example.mvvmhilt.repos.FakeSampleRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class SampleViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UsersViewModel
    private lateinit var fakeRepository: FakeSampleRepoImpl

    @Before
    fun setup() {
        fakeRepository = FakeSampleRepoImpl()
        viewModel = UsersViewModel(fakeRepository)
    }

    @Test
    fun `test inserting data into repoitory success case`() {
        val item1 = User("name1", "craft1")
        val list = arrayListOf(item1)
        viewModel.insertData(list)
        val result = viewModel.roomUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).hasSize(1)
    }

    @Test
    fun `test deleting data from repoository success case`() {
        val item2 = User("name2", "craft2")
        val list = arrayListOf(item2)
        viewModel.insertData(list)
        viewModel.deleteData()
        val result = viewModel.roomUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `test getting user data from api success case`() {
        //fakeRepository.shouldReturnNetworkError(false)
        viewModel.performNetworkRequest()
        val result = viewModel.apiUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isInstanceOf(UiState.Success::class.java)
    }

    @Test
    fun `test getting user data from api error case`() {
        //fakeRepository.shouldReturnNetworkError(true)
        viewModel.performNetworkRequest()
        val result = viewModel.apiUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isInstanceOf(UiState.Error::class.java)
    }

}