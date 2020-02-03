package com.cjmobileapps.githubfollowersandroid.ui.dagger

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.ui.viewmodel.MainViewModel
import com.cjmobileapps.githubfollowersandroid.ui.viewmodel.util.MainViewModelFactory
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val activity: AppCompatActivity) {

    @MainScope
    @Provides
    fun mainViewModel(gitHubFollowersService: GitHubFollowersService, rxDispatchers: RxDispatchers): MainViewModel {
        return ViewModelProviders.of(activity, MainViewModelFactory(gitHubFollowersService, rxDispatchers)) [MainViewModel::class.java]
    }
}
