package com.example.mvvmhilt.views.shows

import androidx.lifecycle.*
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
) : ViewModel() {

    private val _tvShows = MutableLiveData<UiState<TvShowList>?>()
    val tvShows : LiveData<UiState<TvShowList>?> = _tvShows

 fun getTvShows() = viewModelScope.launch {
     _tvShows.postValue(UiState.Loading())
     val tvShowList = getTvShowsUseCase.execute()
     _tvShows.postValue(tvShowList)
 }

}
