package com.cjmobileapps.githubfollowersandroid.dagger.module

import android.content.Context
import com.cjmobileapps.githubfollowersandroid.dagger.GitHubFollowersApplicationScope
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersApi
import com.cjmobileapps.githubfollowersandroid.network.GitHubFollowersService
import com.cjmobileapps.githubfollowersandroid.network.NetworkConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    //Create a cache object part 1.
    @GitHubFollowersApplicationScope
    @Provides
    fun httpCacheDirectory(context: Context): File {
        return File(context.getCacheDir(), NetworkConstants.HTTP_CACHE_DIR)
    }

    //Create a cache object part 2.
    @GitHubFollowersApplicationScope
    @Provides
    fun cache(httpCacheDirectory: File): Cache {
        return Cache(httpCacheDirectory, NetworkConstants.HTTP_CACHE_SIZE)
    }

    //Create a network cache interceptor, setting the max age to 1 minute
    @GitHubFollowersApplicationScope
    @Provides
    fun networkCacheInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

                return response.newBuilder()
                    .header(NetworkConstants.CACHE_CONTROL, cacheControl.toString()).build()
            }
        }
    }

    //Create a logging interceptor
    @GitHubFollowersApplicationScope
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        return loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //Create the httpClient, configure it
    // with cache, network cache interceptor and logging interceptor
    @GitHubFollowersApplicationScope
    @Provides
    fun okHttpClient(cache: Cache,
                     networkCacheInterceptor: Interceptor,
                     loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(networkCacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    //Create the Retrofit with the httpClient
    @GitHubFollowersApplicationScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @GitHubFollowersApplicationScope
    @Provides
    fun gitHubFollowersApi(retrofit: Retrofit): GitHubFollowersApi {
        return retrofit.create(GitHubFollowersApi::class.java)
    }

    @GitHubFollowersApplicationScope
    @Provides
    fun gitHubFollowersService(gitHubFollowersApi: GitHubFollowersApi): GitHubFollowersService {
        return GitHubFollowersService(gitHubFollowersApi)
    }
}