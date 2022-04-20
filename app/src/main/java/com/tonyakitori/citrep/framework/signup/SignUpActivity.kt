package com.tonyakitori.citrep.framework.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.ActivitySignUpBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.login.LoginActivity
import com.tonyakitori.citrep.framework.utils.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        signUpViewModel.accountCreationLoading.observe(this, ::handleLoading)
        signUpViewModel.account.observe(this, ::handleSuccess)
        signUpViewModel.error.observe(this) { longToast(getString(R.string.ops_error)) }
    }

    private fun handleLoading(show: Boolean) {
        binding.progress.isVisible = show
    }

    private fun handleSuccess(accountEntity: AccountEntity) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setUpViews() = with(binding) {

        loginText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        signUpButton.setOnClickListener { handleSignUp() }
    }

    private fun handleSignUp() = with(binding) {

        if (!validateForm()) {
            Toast.makeText(
                this@SignUpActivity,
                "Los datos no son correctos verificalos",
                Toast.LENGTH_SHORT
            ).show()
            return@with
        }

        signUpViewModel.createAccount(
            AccountEntity(
                email = edtEmail.text.toString(),
                password = edtPassword.text.toString(),
                name = edtName.text.toString()
            )
        )
    }

    private fun validateForm(): Boolean = with(binding) {
        return edtEmail.text.toString().isNotBlank()
                && edtName.text.toString().isNotBlank()
                && edtPassword.text.toString().isNotBlank()
                && edtPassword.text.toString() == edtConfirmPassword.text.toString()
    }
}