package com.example.submissionawalaplikasigithubuser.data.retrofit

import com.example.submissionawalaplikasigithubuser.BuildConfig
import com.example.submissionawalaplikasigithubuser.data.response.GithubResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


//https://api.github.com/search/users?q=sidiqpermana

interface ApiService {
    @Headers("Authorization: token" + BuildConfig.KEY)
    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<GithubResponse>
}