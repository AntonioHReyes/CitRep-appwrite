package com.tonyakitori.citrep.framework.ui.screens.main.fragments.profile

import android.content.Intent
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
import com.tonyakitori.citrep.framework.ui.screens.login.LoginActivity
import com.tonyakitori.citrep.framework.utils.createInfoLog
import com.tonyakitori.citrep.framework.utils.longToast
import com.tonyakitori.citrep.framework.utils.tint
import io.appwrite.models.Token
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

        setUpViews()
        setUpObservers()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        verifyAccountItem.setOnClickListener {
            profileViewModel.createEmailVerification()
        }

        accountLogOut.setOnClickListener {
            profileViewModel.logOut()
            val intent = Intent(binding.root.context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setUpObservers() {
        profileViewModel.avatarLoading.observe(viewLifecycleOwner, ::handleAvatarLoading)
        profileViewModel.avatarUrl.observe(viewLifecycleOwner, ::handleAvatarResponse)

        profileViewModel.accountData.observe(viewLifecycleOwner, ::handleAccountData)

        profileViewModel.emailVerificationLoading.observe(
            viewLifecycleOwner,
            ::handleEmailVerificationLoading
        )
        profileViewModel.emailVerification.observe(viewLifecycleOwner, ::handleEmailVerification)
        profileViewModel.emailVerificationError.observe(
            viewLifecycleOwner,
            ::handleEmailVerificationError
        )
    }

    private fun handleEmailVerificationLoading(show: Boolean) = with(binding) {
        generalProgress.isVisible = show
    }

    private fun handleEmailVerification(data: Token?) {
        longToast("Hemos enviado un email a tu correo para continuar con la verificación")
        data?.createInfoLog("CreateEmailVerification")
    }

    private fun handleEmailVerificationError(error: Unit) {
        longToast(getString(R.string.ops_error))
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
        profileStatus.text =
            if (accountEntity?.isVerified == true) getString(R.string.verified) else getString(R.string.with_out_verified)
    }

}