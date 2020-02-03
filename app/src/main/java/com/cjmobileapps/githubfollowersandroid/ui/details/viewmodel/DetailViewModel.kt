package com.cjmobileapps.githubfollowersandroid.ui.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.network.models.User
import com.cjmobileapps.githubfollowersandroid.network.util.ResponseWrapper
import com.cjmobileapps.githubfollowersandroid.util.RxDispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DetailViewModel(private val gitHubFollowersService: GitHubFollowersService, private val rxDispatchers: RxDispatchers): ViewModel() {

    private lateinit var compositeJob: Job
    private val tag = DetailViewModel::class.java.simpleName
    private val userMutableLiveData = MutableLiveData<User>()
    val user: LiveData<User> = userMutableLiveData
    private val errorMutableLiveData = MutableLiveData<String>()
    val error: LiveData<String> = errorMutableLiveData

    fun initCoroutineJobs() {
        compositeJob = Job()

    }

    fun getUsers(userName: String) {
        try {
            GlobalScope.launch(compositeJob + rxDispatchers.io) {
                val userResponse = gitHubFollowersService.getUserAsync(userName).await()


                if(userResponse.isSuccessful) {

                    ResponseWrapper.getData(userResponse)
                    val user = ResponseWrapper.getData(userResponse)
                    withContext(rxDispatchers.main) {
                        userMutableLiveData.value = user
                    }

                } else {
                    val error = ResponseWrapper.getError(userResponse.errorBody())
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