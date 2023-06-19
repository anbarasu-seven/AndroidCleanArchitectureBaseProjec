package com.example.mvvmhilt.presenter.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.domain.dto.TvShowListDto
import com.example.mvvmhilt.domain.dto.UIState
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
) : ViewModel() {

    private val _tvShows = MutableLiveData<UIState<TvShowListDto>?>()
    val tvShows: LiveData<UIState<TvShowListDto>?> = _tvShows

    fun getTvShows() = viewModelScope.launch {
        _tvShows.postValue(UIState.Loading())
        val tvShowList = getTvShowsUseCase.execute()
        _tvShows.postValue(tvShowList)
    }

}
