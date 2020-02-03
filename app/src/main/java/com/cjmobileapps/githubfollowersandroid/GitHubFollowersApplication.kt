package com.cjmobileapps.githubfollowersandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import com.cjmobileapps.githubfollowersandroid.dagger.DaggerGitHubFollowersApplicationComponent
import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationComponent
import com.cjmobileapps.githubfollowersandroid.dagger.module.ContextModule
import timber.log.Timber

class GitHubFollowersApplication : Application() {

    lateinit var gitHubFollowersApplicationComponent: GitHubFollowersApplicationComponent

    companion object {

        fun get(activity: Activity): GitHubFollowersApplication {
            return activity.application as GitHubFollowersApplication
        }

        fun get(context: Context): GitHubFollowersApplication {
            return context.applicationContext as GitHubFollowersApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        gitHubFollowersApplicationComponent = DaggerGitHubFollowersApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()

        gitHubFollowersApplicationComponent.injectGitHubFollowersApplicationComponent(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
