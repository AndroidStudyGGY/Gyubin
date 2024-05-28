package com.gyub.implementationtestexample.xml

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *
 * @author   Gyub
 * @created  2024/05/27
 */

object RetrofitInstance {
    val api: GithubService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }
}

class IssuesRepository {
    private val api = RetrofitInstance.api

    suspend fun getIssues(user: String, repo: String): List<Issue> {
        return api.getIssues(user, repo)
    }
}