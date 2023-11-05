package com.example.submissionawalaplikasigithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes

import com.bumptech.glide.Glide
import com.example.submissionawalaplikasigithubuser.R
import com.example.submissionawalaplikasigithubuser.databinding.ActivityDetailBinding



class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tv_follower,
            R.string.tv_following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("USERNAME")

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (username != null) {

            //persiapan untuk pager
//            val sectionPagerAdapter = SectionPagerAdapter(this, username)
//            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
//            viewPager.adapter = sectionPagerAdapter
//            val tabs: TabLayout = findViewById(R.id.tabs)
//            TabLayoutMediator(tabs, viewPager) { tab, position ->
//                tab.text = resources.getString(TAB_TITLES[position])
//            }.attach()
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUserPhoto)
                binding.tvName.text = it.name
                binding.tvUserName.text = it.login
                binding.tvFollower.text = "${it.followers} Follower"
                binding.tvFollowing.text = "${it.following} Following"
            }
        }
        detailViewModel.loading.observe(this) {
            showLoading(it)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}

