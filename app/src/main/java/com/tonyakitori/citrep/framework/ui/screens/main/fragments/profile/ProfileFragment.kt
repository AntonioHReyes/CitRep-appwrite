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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.FragmentProfileBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.ui.dialogs.VerificationDialog
import com.tonyakitori.citrep.framework.ui.screens.login.LoginActivity
import com.tonyakitori.citrep.framework.utils.longToast
import com.tonyakitori.citrep.framework.utils.tint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val fragmentArgs: ProfileFragmentArgs by navArgs()
    private val verificationDialog by lazy { VerificationDialog(binding.root.context) }

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

    override fun onResume() {
        super.onResume()

        if (!fragmentArgs.userId.isNullOrBlank() && !fragmentArgs.secret.isNullOrBlank()) {
            handleEmailVerification()
            this.arguments?.clear()
        }

    }

    private fun handleEmailVerification() {
        verificationDialog.showDialog()
        profileViewModel.confirmEmailVerification(
            fragmentArgs.userId ?: "",
            fragmentArgs.secret ?: ""
        )
    }

    private fun setUpViews() = with(binding) {
        verifyAccountItem.setOnClickListener {
            profileViewModel.createEmailVerification()
        }

        settingsAccountItem.setOnClickListener { longToast(getString(R.string.not_implemented)) }
        policyAccountItem.setOnClickListener { longToast(getString(R.string.not_implemented)) }

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

        profileViewModel.confirmVerificationLoading.observe(
            viewLifecycleOwner,
            ::handleConfirmEmailLoading
        )
        profileViewModel.confirmVerificationData.observe(
            viewLifecycleOwner,
            ::handleConfirmEmailData
        )
        profileViewModel.confirmVerificationError.observe(
            viewLifecycleOwner,
            ::handleConfirmEmailError
        )
    }

    private fun handleEmailVerificationLoading(show: Boolean) = with(binding) {
        generalProgress.isVisible = show
    }

    private fun handleEmailVerification(tokenId: String?) {
        longToast(getString(R.string.email_verification_send_success))
    }

    private fun handleEmailVerificationError(error: Unit) {
        longToast(getString(R.string.ops_error))
    }

    private fun handleConfirmEmailLoading(show: Boolean) {
        if (show) verificationDialog.dialogStatus = VerificationDialog.VerificationStatus.PROGRESS
    }

    private fun handleConfirmEmailData(data: String?) {
        verificationDialog.dialogStatus = VerificationDialog.VerificationStatus.SUCCESS
        lifecycleScope.launch {
            profileViewModel.getAccountData()
            delay(2_500)
            verificationDialog.dismiss()
        }
    }

    private fun handleConfirmEmailError(event: Unit) {
        verificationDialog.dialogStatus = VerificationDialog.VerificationStatus.ERROR
        longToast(getString(R.string.ops_error))
        lifecycleScope.launch {
            delay(2_500)
            verificationDialog.dismiss()
        }
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
        verifyAccountItem.isVisible = accountEntity?.isVerified == false
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