package com.tonyakitori.citrep.framework.ui.screens.main.fragments.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.FragmentHomeBinding
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.framework.ui.adapters.PostsAdapter
import com.tonyakitori.citrep.framework.ui.screens.post.NewPostDialogFragment
import com.tonyakitori.citrep.framework.utils.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private val adapter: PostsAdapter by lazy { PostsAdapter(::handleActionsClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpViews()
        setUpObservers()
        setUpHomeToolbar()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        swipeRefresh.setOnRefreshListener {
            homeViewModel.getSavedPosts()
        }

        rcvPostsCreated.adapter = adapter
    }

    private fun handleActionsClick() {
        longToast(getString(R.string.not_implemented))
    }

    private fun setUpObservers() {
        homeViewModel.avatar.observe(viewLifecycleOwner, ::handleAvatarBind)
        homeViewModel.postsLoading.observe(viewLifecycleOwner, ::handlePostsLoading)
        homeViewModel.posts.observe(viewLifecycleOwner, ::handlePostsList)
        homeViewModel.postsError.observe(viewLifecycleOwner, ::handlePostsError)
    }

    private fun handlePostsLoading(show: Boolean) = with(binding) {
        swipeRefresh.isRefreshing = show
    }

    private fun handlePostsList(list: List<PostEntity>) {
        adapter.addPosts(list)
    }

    private fun handlePostsError(error: Unit) {
        longToast(getString(R.string.ops_error))
    }

    private fun handleAvatarBind(avatar: ByteArray?) = with(binding) {
        avatar?.let {
            homeToolbarAction.avatarImage.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    avatar,
                    0,
                    avatar.size
                )
            )
        }
    }

    private fun setUpHomeToolbar() = with(binding) {
        homeToolbarAction.apply {
            complainDescription.setOnClickListener {
                NewPostDialogFragment().show(
                    childFragmentManager,
                    tag
                )
            }

            loadFile.setOnClickListener {
                NewPostDialogFragment().show(
                    childFragmentManager,
                    tag
                )
            }
        }
    }


}