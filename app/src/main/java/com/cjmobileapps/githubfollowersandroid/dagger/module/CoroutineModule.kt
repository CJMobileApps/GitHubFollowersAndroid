package com.cjmobileapps.githubfollowersandroid.dagger.module

import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationScope
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import dagger.Module
import dagger.Provides

@Module
class CoroutineModule {

    @GitHubFollowersApplicationScope
    @Provides
    fun rxDispatcher(): RxDispatchers {
        return RxDispatchers()
    }
}
