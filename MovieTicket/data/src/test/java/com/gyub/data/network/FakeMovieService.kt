package com.gyub.data.network

import com.gyub.data.network.model.MovieDetailResponse
import com.gyub.data.network.model.MovieInfoResponse
import com.gyub.data.network.model.MovieItemInfoResponse
import com.gyub.data.network.model.MovieListResponse
import com.gyub.data.network.model.MovieResponse
import com.gyub.data.network.retrofit.MovieService

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
class FakeMovieService : MovieService {
    override suspend fun getMovies(page: String, size: String): MovieResponse {
        return MovieResponse(
            fakeMovieResponseItemResponse.copy(
                movieItemsResponse = fakeMovieResponseItemResponse.movieItemsResponse.take(size.toInt())
            )
        )
    }

    override suspend fun getMovieDetail(movieCode: String): MovieDetailResponse {
        return fakeMovieInfoResponse
    }

    private val fakeMovieInfoResponse = MovieDetailResponse(
        MovieInfoResponse(
            movieItemInfoResponse = MovieItemInfoResponse(
                movieCode = "1234",
                movieName = "Danny Preston",
                movieNameEn = "Everett Poole",
                movieNameOg = null,
                showTime = "faucibus",
                productionYear = "vulputate",
                openDate = "legimus",
                productionStatus = "senectus",
                type = "agam",
                nationResponses = listOf(),
                genreResponses = listOf(),
                directorResponses = listOf(),
                actorResponses = listOf(),
                showTypeResponses = listOf(),
                companies = listOf(),
                auditResponses = listOf(),
                staffResponses = listOf()
            ), source = "possim",
        )
    )


    private val fakeMovieResponseItemResponse = MovieListResponse(
        totalCount = 10,
        source = "영화진흥위원회",
        movieItemsResponse = listOf(
            MovieListResponse.MovieItemResponse(
                code = "20227851",
                movieName = "SIFFF2022 오감만족 국제단편경선 3",
                englishName = "Tasty Short Film Competition 3",
                productionYear = "2022",
                openDate = "",
                type = "옴니버스",
                productionStatus = "기타",
                nationList = "기타",
                genreList = "기타",
                nationName = "기타",
                genreName = "기타",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20227850",
                movieName = "SIFFF2022 오감만족 국제단편경선 2",
                englishName = "Tasty Short Film Competition 2",
                productionYear = "2022",
                openDate = "",
                type = "옴니버스",
                productionStatus = "기타",
                nationList = "기타",
                genreList = "기타",
                nationName = "기타",
                genreName = "기타",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20242903",
                movieName = "파노라마 스월",
                englishName = "Panorama Swirl",
                productionYear = "2024",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "중국",
                genreList = "드라마,가족",
                nationName = "중국",
                genreName = "드라마",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20227827",
                movieName = "SIFFF2022 오감만족 국제단편경선 1",
                englishName = "Tasty Short Film Competition 1",
                productionYear = "2022",
                openDate = "",
                type = "옴니버스",
                productionStatus = "기타",
                nationList = "기타",
                genreList = "기타",
                nationName = "기타",
                genreName = "기타",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20242902",
                movieName = "비건 식탁-연결된 세계",
                englishName = "Connected:VeganicTable",
                productionYear = "2022",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "한국",
                genreList = "다큐멘터리",
                nationName = "한국",
                genreName = "다큐멘터리",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20242901",
                movieName = "아빠의 금고",
                englishName = "What my dad left",
                productionYear = "2022",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "한국",
                genreList = "드라마",
                nationName = "한국",
                genreName = "드라마",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20242895",
                movieName = "국밥집, 옛날의 뚝방길",
                englishName = "Time spent with mom",
                productionYear = "2022",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "한국",
                genreList = "드라마",
                nationName = "한국",
                genreName = "드라마",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20223555",
                movieName = "1급수 인간",
                englishName = "A+ Human",
                productionYear = "2021",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "한국",
                genreList = "드라마,판타지",
                nationName = "한국",
                genreName = "드라마",
                directorResponses = listOf(
                    MovieListResponse.MovieItemResponse.DirectorResponse(name = "이진영")
                ),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20242894",
                movieName = "형수의 팬티는 욕정 유발자",
                englishName = "",
                productionYear = "2021",
                openDate = "",
                type = "장편",
                productionStatus = "기타",
                nationList = "일본",
                genreList = "성인물(에로)",
                nationName = "일본",
                genreName = "성인물(에로)",
                directorResponses = emptyList(),
                companies = emptyList()
            ),
            MovieListResponse.MovieItemResponse(
                code = "20225928",
                movieName = "쿠키 커피 도시락",
                englishName = "Cookie Coffee Dosirak",
                productionYear = "2022",
                openDate = "",
                type = "단편",
                productionStatus = "기타",
                nationList = "한국",
                genreList = "애니메이션",
                nationName = "한국",
                genreName = "애니메이션",
                directorResponses = listOf(
                    MovieListResponse.MovieItemResponse.DirectorResponse(name = "강민지"),
                    MovieListResponse.MovieItemResponse.DirectorResponse(name = "김혜미"),
                    MovieListResponse.MovieItemResponse.DirectorResponse(name = "이경화"),
                    MovieListResponse.MovieItemResponse.DirectorResponse(name = "한병아")
                ),
                companies = emptyList()
            )
        ),
    )
}