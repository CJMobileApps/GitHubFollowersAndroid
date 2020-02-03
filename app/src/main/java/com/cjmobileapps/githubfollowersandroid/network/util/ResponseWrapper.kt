package com.cjmobileapps.githubfollowersandroid.network.util

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object ResponseWrapper {

    fun <T> getData(response: Response<T>) : T? {
        return response.body()
    }

    fun getError(errorBody: ResponseBody?) : String {
        val jsonString = errorBody?.string()

        return Gson().fromJson(jsonString, Error::class.java).message
    }
}

data class Error(
    val message: String,
    val documentation_url: String
)
