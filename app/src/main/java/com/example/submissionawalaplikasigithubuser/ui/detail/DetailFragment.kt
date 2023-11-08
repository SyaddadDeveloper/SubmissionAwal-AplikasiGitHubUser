package com.example.submissionawalaplikasigithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalaplikasigithubuser.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var position = 0
    private var username: String = ""

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_USERNAME = "0"
        const val ARG_POSITION = "Syaddad"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "test"
        }


        detailViewModel.getUserFollowing(username)
        detailViewModel.getUserFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFragment.layoutManager = layoutManager

        detailViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) {
                val adapter = DetailUserAdapter()
                adapter.submitList(it)
                binding.rvFragment.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                val adapter = DetailUserAdapter()
                adapter.submitList(it)
                binding.rvFragment.adapter = adapter
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarFollow.visibility = View.VISIBLE
            } else {
                progressBarFollow.visibility = View.GONE
            }
        }
    }
}