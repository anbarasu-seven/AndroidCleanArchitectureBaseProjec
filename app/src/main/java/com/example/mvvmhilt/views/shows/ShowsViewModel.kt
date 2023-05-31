package com.example.mvvmhilt.views.shows

import androidx.lifecycle.*
import com.anushka.tmdbclient.data.model.tvshow.ShowsResponse
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import com.example.mvvmhilt.domain.usecase.UpdateTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
) : ViewModel() {

    private val _tvShows = MutableLiveData<UiState<ShowsResponse>?>()
    val tvShows : LiveData<UiState<ShowsResponse>?> = _tvShows

 fun getTvShows() = viewModelScope.launch {
     val tvShowList = getTvShowsUseCase.execute()
     _tvShows.postValue(tvShowList)
 }

}
