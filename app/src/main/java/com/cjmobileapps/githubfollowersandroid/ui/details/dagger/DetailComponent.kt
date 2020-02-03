package com.cjmobileapps.githubfollowersandroid.ui.details.dagger

import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationComponent
import com.cjmobileapps.githubfollowersandroid.ui.dagger.MainScope
import com.cjmobileapps.githubfollowersandroid.ui.details.DetailActivity
import dagger.Component

@MainScope
@Component(modules = [DetailModule::class], dependencies = [GitHubFollowersApplicationComponent::class])
interface DetailComponent {

    fun inject(detailActivity: DetailActivity)
}
