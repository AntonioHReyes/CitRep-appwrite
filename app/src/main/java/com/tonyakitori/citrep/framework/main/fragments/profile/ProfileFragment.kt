package com.tonyakitori.citrep.framework.main.fragments.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.FragmentProfileBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.utils.tint
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        profileViewModel.avatarLoading.observe(viewLifecycleOwner, ::handleAvatarLoading)
        profileViewModel.avatarUrl.observe(viewLifecycleOwner, ::handleAvatarResponse)

        profileViewModel.accountData.observe(viewLifecycleOwner, ::handleAccountData)
    }

    private fun handleAvatarLoading(show: Boolean) = with(binding) {
        avatarProgress.isVisible = show
        profileAvatar.isVisible = !show
    }

    private fun handleAvatarResponse(avatar: ByteArray?) = with(binding) {
        avatar?.let {
            profileAvatar.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, avatar.size))
        }
    }

    private fun handleAccountData(accountEntity: AccountEntity?) = with(binding) {
        profileName.text = accountEntity?.name.orEmpty()
        profileVerified.isVisible = true
        profileVerified.tint(
            ContextCompat.getColor(
                binding.root.context,
                if (accountEntity?.isVerified == true) R.color.green else R.color.red
            )
        )
    }

}