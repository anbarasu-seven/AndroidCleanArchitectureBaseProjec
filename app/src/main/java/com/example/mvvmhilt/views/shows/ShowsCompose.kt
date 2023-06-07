package com.example.mvvmhilt.views.shows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mvvmhilt.common.utils.extn.ShowToast
import com.example.mvvmhilt.data.models.UiState
import com.example.mvvmhilt.data.models.tvshow.TvShow
import com.example.mvvmhilt.data.models.tvshow.TvShowList

@Composable
fun ShowsCompose(
    navController: NavHostController,
    showsViewModel: ShowsViewModel = hiltViewModel()
) {
    val state = mutableStateOf(showsViewModel.tvShows.collectAsState().value)
    when (state.value) {
        is UiState.Loading -> {
            Loader()
        }
        is UiState.Success -> state.value?.data?.let {
            ShowsList(it)
        }
        is UiState.Error -> state.value?.message?.let {
            ShowToast(it)
        }
        else -> {}
    }
}

@Composable
fun ShowsList(data: TvShowList) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(data.tvShows) { item ->
            MovieCard(item)
        }
    }
}

@Composable
fun Loader() {
    Box(
        contentAlignment = Alignment.Center, // you apply alignment to all children
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            strokeWidth = 3.dp
        )
    }
}

@Composable
fun MovieCard(item: TvShow) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                contentDescription = null, // Provide a meaningful description if needed
                model = "https://image.tmdb.org/t/p/w500" + item.posterPath,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = item.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.overview,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}