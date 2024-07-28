package com.gyub.movieticket.movie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gyub.movieticket.movie.MovieViewModel
import com.gyub.movieticket.movie.model.MovieUiModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@Composable
fun MovieRoute(
    viewModel: MovieViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    onMovieClick: (String) -> Unit,
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    MovieContent(
        movies = movies,
        onMovieClick = onMovieClick
    )
}

@Composable
fun MovieContent(
    movies: LazyPagingItems<MovieUiModel>,
    onMovieClick: (String) -> Unit,
) {
    MovieList(
        movies = movies,
        onMovieClick = onMovieClick
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieUiModel>,
    onMovieClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = movies.itemCount,
        ) {
            MovieItem(
                movie = movies[it]!!,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieUiModel,
    onMovieClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            text = "영화 이름: ${movie.movieName}",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "장르 ${movie.genreName}",
            fontSize = 15.sp
        )
        Text(
            text = if (movie.isAvailableScreening) "개봉" else "상영 불가",
            fontSize = 15.sp
        )
        if (movie.isAvailableScreening) {
            Button(onClick = { onMovieClick(movie.code) }) {
                Text(text = "예매")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    val movie = MovieUiModel(
        code = "12123",
        movieName = "데드풀과 울버린",
        genreName = "액션",
        isAvailableScreening = false,
        price = 3650
    )
    MovieItem(
        movie = movie,
        onMovieClick = {}
    )
}
