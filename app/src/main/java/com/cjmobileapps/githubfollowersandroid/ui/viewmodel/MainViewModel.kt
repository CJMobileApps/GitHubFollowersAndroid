package com.cjmobileapps.githubfollowersandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.network.models.Follower
import com.cjmobileapps.githubfollowersandroid.network.util.ResponseWrapper
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel(private val gitHubFollowersService: GitHubFollowersService, private val rxDispatchers: RxDispatchers): ViewModel() {

    private lateinit var compositeJob: Job
    private val tag = MainViewModel::class.java.simpleName
    private val followersMutableLiveData = MutableLiveData<List<Follower>>()
    val follower: LiveData<List<Follower>> = followersMutableLiveData
    private val errorMutableLiveData = MutableLiveData<String>()
    val error: LiveData<String> = errorMutableLiveData

    fun initCoroutineJobs() {
        compositeJob = Job()

    }

    fun getFollowers(userName: String) {
        try {
            GlobalScope.launch(compositeJob + rxDispatchers.io) {
                val followersResponse = gitHubFollowersService.getFollowersAsync(userName).await()


                if(followersResponse.isSuccessful) {

                    ResponseWrapper.getData(followersResponse)//  .retrieveSomething(followersResponse)//  .getData(followersResponse)
                    //Log.d("HERE_", "followers: " + ResponseWrapper.getData(followersResponse))
                    val followers = ResponseWrapper.getData(followersResponse)
                    withContext(rxDispatchers.main) {
                        followersMutableLiveData.value = followers
                    }

                } else {
                    val error = ResponseWrapper.getError(followersResponse.errorBody())
                    withContext(rxDispatchers.main) {
                        errorMutableLiveData.value = error
                    }
                }
            }
        } catch (e: Exception) {
            Timber.tag(tag).e("getFollowers error message: %s", e.toString())
        }
    }

    override fun onCleared() {
        compositeJob.cancel()
    }
}
