package com.cjmobileapps.githubfollowersandroid.ui.dagger

import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationComponent
import com.cjmobileapps.githubfollowersandroid.ui.MainActivity
import dagger.Component

@MainScope
@Component(modules = [MainModule::class], dependencies = [GitHubFollowersApplicationComponent::class])
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}