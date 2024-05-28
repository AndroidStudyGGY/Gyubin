package com.gyub.implementationtestexample.xml

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
class MainViewModel : ViewModel() {

    private val _dataList: MutableStateFlow<List<Data>?> = MutableStateFlow(listOf())
    val dataList = _dataList.asStateFlow()

    private val repository = IssuesRepository()

    private val banners = listOf(
        Banner("https://picsum.photos/id/103/2592/1936"),
        Banner("https://picsum.photos/id/104/2592/1936")
    )
    val bannersWithPositions = listOf(
        Pair(banners[0], 2),
        Pair(banners[1], 5)
    )

    fun fetchIssues(userName: String, repo: String) {
        viewModelScope.launch {
            val issues = repository.getIssues(userName, repo).map { Data.IssueData(it) } as MutableList<Data>
            insertBanners(issues, bannersWithPositions)

            _dataList.value = issues
        }
    }

    private fun insertBanners(issues: MutableList<Data>, bannersWithPositions: List<Pair<Banner, Int>>) {
        for ((banner, position) in bannersWithPositions) {
            if (position in 0..issues.size) {
                issues.add(position, Data.BannerData(banner))
            }
        }
    }
}

sealed interface Data {
    data class IssueData(val data: Issue) : Data
    data class BannerData(val data: Banner) : Data
}