package com.example.mvvmhilt.views.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mvvmhilt.domain.usecase.GetShowsUseCase
import com.example.mvvmhilt.domain.usecase.UpdateTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getTvShowsUseCase: GetShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
) : ViewModel() {

 fun getTvShows() = liveData {
     val tvShowList = getTvShowsUseCase.execute()
     emit(tvShowList)
 }

 fun updateTvShows() = liveData {
     val tvShowList = updateTvShowsUseCase.execute()
     emit(tvShowList)
 }
}
