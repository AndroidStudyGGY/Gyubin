package com.gyub.movieticket.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gyub.domain.model.MovieModel
import com.gyub.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {
    private val _movies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesUseCase()
                .cachedIn(viewModelScope)
                .catch {
                    Log.d("TAG", " - :에러에러에러에러 ")
                }
                .collect {
                    _movies.value = it
                }
        }
    }
}