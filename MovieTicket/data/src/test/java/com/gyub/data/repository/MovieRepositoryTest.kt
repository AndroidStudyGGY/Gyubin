package com.gyub.data.repository

import androidx.paging.testing.asSnapshot
import com.gyub.data.datasource.MoviesPagingSource
import com.gyub.data.datasource.remote.MovieDataSource
import com.gyub.data.network.FakeMovieService
import com.gyub.domain.model.MovieModel
import com.gyub.domain.repository.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@RunWith(RobolectricTestRunner::class)
class MovieRepositoryTest {

    private val fakeMovieService = FakeMovieService()
    private val movieDataSource = MovieDataSource(fakeMovieService)
    private val moviesPagingSource = MoviesPagingSource(movieDataSource)

    private val movieRepository = MovieRepositoryImpl(
        movieDataSource = movieDataSource,
        moviesPagingSource = moviesPagingSource
    )

    @Test
    fun `영화 페이징 데이터를 불러온다`() = runTest {
        assertEquals(
            expected = fakeMovies.first()
                .code,
            actual = movieRepository.getMovies().asSnapshot()
                .first()
                .code
        )
    }

    private val fakeMovies = listOf(
        MovieModel(
            code = "20227851",
            movieName = "SIFFF2022 오감만족 국제단편경선 3",
            genreName = "기타",
        ),
        MovieModel(
            code = "20227850",
            movieName = "SIFFF2022 오감만족 국제단편경선 2",
            genreName = "기타",
        ),
        MovieModel(
            code = "20242903",
            movieName = "파노라마 스월",
            genreName = "드라마",
        ),
        MovieModel(
            code = "20227827",
            movieName = "SIFFF2022 오감만족 국제단편경선 1",
            genreName = "기타",
        ),
        MovieModel(
            code = "20242902",
            movieName = "비건 식탁-연결된 세계",
            genreName = "다큐멘터리",
        ),
        MovieModel(
            code = "20242901",
            movieName = "아빠의 금고",
            genreName = "드라마",
        ),
        MovieModel(
            code = "20242895",
            movieName = "국밥집, 옛날의 뚝방길",
            genreName = "드라마",
        ),
        MovieModel(
            code = "20223555",
            movieName = "1급수 인간",
            genreName = "드라마",
        ),
        MovieModel(
            code = "20242894",
            movieName = "형수의 팬티는 욕정 유발자",
            genreName = "성인물(에로)",
        ),
        MovieModel(
            code = "20225928",
            movieName = "쿠키 커피 도시락",
            genreName = "애니메이션",
        )
    )
}