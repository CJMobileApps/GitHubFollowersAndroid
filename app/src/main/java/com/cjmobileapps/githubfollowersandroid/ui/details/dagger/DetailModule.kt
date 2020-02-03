package com.cjmobileapps.githubfollowersandroid.ui.details.dagger

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.ui.details.viewmodel.DetailViewModel
import com.cjmobileapps.githubfollowersandroid.ui.viewmodel.util.MainViewModelFactory
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import dagger.Module
import dagger.Provides

@Module
class DetailModule(private val activity: AppCompatActivity) {

    @DetailScope
    @Provides
    fun detailViewModel(gitHubFollowersService: GitHubFollowersService, rxDispatchers: RxDispatchers): DetailViewModel {
        return ViewModelProviders.of(activity, MainViewModelFactory(gitHubFollowersService, rxDispatchers)) [DetailViewModel::class.java]
    }
}
