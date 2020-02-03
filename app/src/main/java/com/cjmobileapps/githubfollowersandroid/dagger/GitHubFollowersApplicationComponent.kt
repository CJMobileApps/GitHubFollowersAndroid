package com.cjmobileapps.githubfollowersandroid.dagger

import android.content.Context
import com.cjmobileapps.githubfollowersandroid.GitHubFollowersApplication
import com.cjmobileapps.githubfollowersandroid.dagger.module.ContextModule
import com.cjmobileapps.githubfollowersandroid.dagger.module.CoroutineModule
import com.cjmobileapps.githubfollowersandroid.dagger.module.NetworkModule
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import dagger.Component

@GitHubFollowersApplicationScope
@Component(modules = [ContextModule::class, NetworkModule::class, CoroutineModule::class])
interface GitHubFollowersApplicationComponent {

    fun provideContext(): Context

    fun provideGitHubFollowersService(): GitHubFollowersService

    fun provideRxDispatchers(): RxDispatchers

    fun injectGitHubFollowersApplicationComponent(gitHubFollowersApplication: GitHubFollowersApplication)
}
