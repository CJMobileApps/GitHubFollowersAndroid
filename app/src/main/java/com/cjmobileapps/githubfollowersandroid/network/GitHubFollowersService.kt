package com.cjmobileapps.githubfollowersandroid.network

class GitHubFollowersService(private val gitHubFollowersApi: GitHubFollowersApi) {

    fun getFollowersAsync(userName: String) = gitHubFollowersApi.getFollowersAsync(userName)

    fun getUserAsync(userName: String) = gitHubFollowersApi.getUserAsync(userName)

}
