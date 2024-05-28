package com.gyub.implementationtestexample.xml

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
interface GithubService {
    @GET("/repos/{github_username}/{repository_name}/issues")
    suspend fun getIssues(@Path("github_username") userName: String, @Path("repository_name") repoName: String): List<Issue>
}