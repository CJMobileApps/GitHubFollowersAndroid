package com.cjmobileapps.githubfollowersandroid.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cjmobileapps.githubfollowersandroid.GitHubFollowersApplication
import com.cjmobileapps.githubfollowersandroid.R
import com.cjmobileapps.githubfollowersandroid.ui.dagger.DaggerMainComponent
import com.cjmobileapps.githubfollowersandroid.ui.dagger.MainModule
import com.cjmobileapps.githubfollowersandroid.ui.viewmodel.MainViewModel
import com.cjmobileapps.githubfollowersandroid.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var mainAdapter: MainAdapter? = null

    private var searchMenuItem: MenuItem? = null

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gitHubFollowersApplication = GitHubFollowersApplication.get(this)

        DaggerMainComponent.builder()
            .mainModule(MainModule(this))
            .gitHubFollowersApplicationComponent(gitHubFollowersApplication.gitHubFollowersApplicationComponent)
            .build()
            .inject(this)

        setUpDefaultToolbar()
        mainViewModel.initCoroutineJobs()
        mainViewModel.follower.observe(this, Observer { followers ->
            hideKeyboard()

            if(mainAdapter == null) {
                mainAdapter = MainAdapter(followers)
                main_followers.adapter = mainAdapter
            } else {
                mainAdapter?.followers = followers
                mainAdapter?.notifyDataSetChanged()
            }
            main_progressbar.visibility = View.GONE
        })
        mainViewModel.error.observe(this, Observer { error ->
            main_progressbar.visibility = View.GONE
            Snackbar.make(main_container, error, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show()
        })

        main_followers.layoutManager = GridLayoutManager(this, 3)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        searchMenuItem = menu?.findItem(R.id.action_search)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                setUpSearchToolbar()
                true
            }
            android.R.id.home -> {
                setUpDefaultToolbar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpDefaultToolbar() {
        setSupportActionBar(main_toolbarDefault)
        supportActionBar?.apply {
            title = "Home"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
        main_toolbarSearch.visibility = View.GONE
        searchMenuItem?.isVisible = true

    }

    private fun setUpSearchToolbar() {
        setSupportActionBar(main_toolbarSearch)
        main_toolbarSearch.visibility = View.VISIBLE
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        searchMenuItem?.isVisible = false
        myEditText.requestFocus()
        showKeyboard()



        myEditText.setOnEditorActionListener { editText, actionId, keyEvent ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    main_progressbar.visibility = View.VISIBLE
                    mainViewModel.getFollowers(editText.text.toString())
                    setUpDefaultToolbar()
                    true
                }
                else -> false
            }
        }
    }
}

//TODO add progress bar
