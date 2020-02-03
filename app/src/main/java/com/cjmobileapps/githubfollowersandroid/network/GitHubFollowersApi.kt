package com.cjmobileapps.githubfollowersandroid.network

import com.cjmobileapps.githubfollowersandroid.network.models.Follower
import com.cjmobileapps.githubfollowersandroid.network.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubFollowersApi {

    @GET("users/{userName}/followers")
    fun getFollowersAsync(@Path("userName") userName: String) : Deferred<Response<List<Follower>>>

    @GET("users/{userName}")
    fun getUserAsync(@Path("userName") userName: String) : Deferred<Response<User>>
}
