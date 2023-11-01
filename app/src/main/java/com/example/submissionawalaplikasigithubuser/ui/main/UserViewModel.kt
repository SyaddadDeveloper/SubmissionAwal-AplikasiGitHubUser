package com.example.submissionawalaplikasigithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalaplikasigithubuser.data.response.GithubResponse
import com.example.submissionawalaplikasigithubuser.data.response.ItemsItem
import com.example.submissionawalaplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel:ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainActivity"
        private const val USER_NAME = "syaddad"
    }

    init {
        setReviewData()
    }

    private fun setReviewData() {
        _isLoading.value = true
        val client = ApiConfig.ApiConfig.getApiService().getUser(USER_NAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value =response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false

                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}