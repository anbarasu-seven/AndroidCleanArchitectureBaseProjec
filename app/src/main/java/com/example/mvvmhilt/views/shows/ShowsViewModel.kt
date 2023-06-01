package com.example.mvvmhilt.views.shows

import androidx.lifecycle.*
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import com.example.mvvmhilt.domain.usecase.DeleteShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
    private val updateTvShowsUseCase: DeleteShowsUseCase
) : ViewModel() {

    private val _tvShows = MutableLiveData<UiState<TvShowList>?>()
    val tvShows : LiveData<UiState<TvShowList>?> = _tvShows

 fun getTvShows() = viewModelScope.launch {
     val tvShowList = getTvShowsUseCase.execute()
     _tvShows.postValue(tvShowList)
 }

}
