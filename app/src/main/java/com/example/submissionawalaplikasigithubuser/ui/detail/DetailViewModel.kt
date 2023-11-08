package com.example.submissionawalaplikasigithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalaplikasigithubuser.data.response.DetailUserResponse
import com.example.submissionawalaplikasigithubuser.data.response.ItemsItem
import com.example.submissionawalaplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    private val _userFollower = MutableLiveData<List<ItemsItem>>()
    val userFollower: LiveData<List<ItemsItem>> = _userFollower

    private val _userFollowing = MutableLiveData<List<ItemsItem>>()
    val userFollowing: LiveData<List<ItemsItem>> = _userFollowing

    private var isloaded = false
    private var isfollowerloaded = false
    private var isfollowingloaded = false

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(username: String) {
        if (!isloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userDetail.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isloaded = true
        }
    }

    fun getUserFollowing(username: String) {
        if (!isfollowingloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowing(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollowing.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowingloaded = true
        }
    }

    fun getUserFollower(username: String) {
        if (!isfollowerloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowers(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollower.postValue(response.body())

                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowerloaded = true
        }
    }
}