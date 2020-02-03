package com.cjmobileapps.githubfollowersandroid.ui.viewmodel.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.ui.details.viewmodel.DetailViewModel
import com.cjmobileapps.githubfollowersandroid.ui.viewmodel.MainViewModel
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers

class MainViewModelFactory(
    private val gitHubFollowersService: GitHubFollowersService,
    private val rxDispatchers: RxDispatchers
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(gitHubFollowersService, rxDispatchers) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(gitHubFollowersService, rxDispatchers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
