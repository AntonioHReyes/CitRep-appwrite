package com.tonyakitori.citrep.framework.ui.main.fragments.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tonyakitori.citrep.databinding.FragmentHomeBinding
import com.tonyakitori.citrep.framework.ui.post.NewPostDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpObservers()
        setUpHomeToolbar()

        return binding.root
    }

    private fun setUpObservers() {
        homeViewModel.avatar.observe(viewLifecycleOwner, ::handleAvatarBind)
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
        }
    }


}