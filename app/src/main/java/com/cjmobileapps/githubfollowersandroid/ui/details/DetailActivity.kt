package com.cjmobileapps.githubfollowersandroid.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cjmobileapps.githubfollowersandroid.GitHubFollowersApplication
import com.cjmobileapps.githubfollowersandroid.R
import com.cjmobileapps.githubfollowersandroid.ui.details.dagger.DaggerDetailComponent
import com.cjmobileapps.githubfollowersandroid.ui.details.dagger.DetailModule
import com.cjmobileapps.githubfollowersandroid.ui.details.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val gitHubFollowersApplication = GitHubFollowersApplication.get(this)

        DaggerDetailComponent.builder()
            .detailModule(DetailModule(this))
            .gitHubFollowersApplicationComponent(gitHubFollowersApplication.gitHubFollowersApplicationComponent)
            .build()
            .inject(this)

        setUpToolbar()
        detailViewModel.initCoroutineJobs()
        detailViewModel.user.observe(this, Observer { user ->

            Picasso.get().load(user.avatar_url).into(detail_overlayAvatar)
            Picasso.get().load(user.avatar_url).into(detail_avatar)
            detail_login.text = user.login
            detail_name.text = user.name
            detail_followersCount.text = user.followers.toString()
            detail_followingCount.text = user.following.toString()
            detail_repositoriesCount.text = user.public_repos.toString()

            val location = user.location
            if(!location.isNullOrEmpty()) {
                detail_location.text = location
            }
            val email = user.email
            if(!email.isNullOrEmpty()) {
                detail_email.text = email
            }
        })

        val userName = intent.getStringExtra(USER_NAME) ?: ""
        detailViewModel.getUsers(userName)
    }

    private fun setUpToolbar() {
        setSupportActionBar(detail_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val USER_NAME = "username"
    }
}
