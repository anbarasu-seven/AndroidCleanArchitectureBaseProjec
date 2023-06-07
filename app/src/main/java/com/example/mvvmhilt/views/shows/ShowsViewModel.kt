package com.example.mvvmhilt.views.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.tvshow.TvShowList
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
) : ViewModel() {

    private val _tvShows = MutableStateFlow<UiState<TvShowList>?>(UiState.Loading())
    val tvShows: StateFlow<UiState<TvShowList>?> = _tvShows

    init {
        getTvShows()
    }

    fun getTvShows() = viewModelScope.launch {
        _tvShows.emit(UiState.Loading())
        val tvShowList = getTvShowsUseCase.execute()
        _tvShows.emit(tvShowList)
    }

}
