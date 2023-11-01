package com.example.submissionawalaplikasigithubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalaplikasigithubuser.data.response.GithubResponse
import com.example.submissionawalaplikasigithubuser.data.response.ItemsItem
import com.example.submissionawalaplikasigithubuser.data.retrofit.ApiConfig
import com.example.submissionawalaplikasigithubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
        private const val USER_NAME = "syaddad"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // berdasarkan warning tertulis Replace function call with indexed accessor. jadi saya menerapkan sarannya
        val userVIewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]
        userVIewModel.listUser.observe(this) { restaurant ->
            setReviewData(restaurant)
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        findUser()

        userVIewModel.listUser.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }
        userVIewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun findUser() {
        showLoading(true)
        val client =ApiConfig.ApiConfig.getApiService().getUser(USER_NAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody.items)

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setReviewData(item: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(item)
        binding.rvUsers.adapter = adapter



    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}