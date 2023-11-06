package com.example.submissionawalaplikasigithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2

import com.bumptech.glide.Glide
import com.example.submissionawalaplikasigithubuser.R
import com.example.submissionawalaplikasigithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
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
            val sectionPagerAdapter = SectionsPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()



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

