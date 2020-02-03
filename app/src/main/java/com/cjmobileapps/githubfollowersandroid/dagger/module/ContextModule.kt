package com.cjmobileapps.githubfollowersandroid.dagger.module

import android.content.Context
import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(context: Context) {
    private val context = context.applicationContext

    @GitHubFollowersApplicationScope
    @Provides
    fun context() : Context {
        return context
    }
}
