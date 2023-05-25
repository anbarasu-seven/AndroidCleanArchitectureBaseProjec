package com.example.mvvmhilt.views.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmhilt.MainCoroutineRule
import com.example.mvvmhilt.data.api.room.getOrAwaitValueTest
import com.example.mvvmhilt.data.models.Resource
import com.example.mvvmhilt.data.models.UserData
import com.example.mvvmhilt.data.models.UserResponse
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

    private lateinit var viewModel: SampleViewModel
    private lateinit var fakeRepository: FakeSampleRepoImpl

    @Before
    fun setup() {
        fakeRepository = FakeSampleRepoImpl()
        viewModel = SampleViewModel(fakeRepository)
    }

    @Test
    fun `test inserting data into repoitory success case`() {
        val item1 = UserData("name1", "craft1")
        val list = arrayListOf(item1)
        viewModel.insertData(list)
        val result = viewModel.roomUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).hasSize(1)
    }

    @Test
    fun `test deleting data from repoository success case`() {
        val item2 = UserData("name2", "craft2")
        val list = arrayListOf(item2)
        viewModel.insertData(list)
        viewModel.deleteData()
        val result = viewModel.roomUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `test getting user data from api success case`() {
        fakeRepository.shouldReturnNetworkError(false)
        viewModel.performNetworkRequest()
        val result = viewModel.apiUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `test getting user data from api error case`() {
        fakeRepository.shouldReturnNetworkError(true)
        viewModel.performNetworkRequest()
        val result = viewModel.apiUsersData.getOrAwaitValueTest()
        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

}