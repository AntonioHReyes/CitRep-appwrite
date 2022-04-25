package com.tonyakitori.citrep.framework.ui.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.ActivityLoginBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.domain.exceptions.AccountExceptions
import com.tonyakitori.citrep.framework.ui.screens.main.HomeActivity
import com.tonyakitori.citrep.framework.ui.screens.signup.SignUpActivity
import com.tonyakitori.citrep.framework.utils.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginVm: LoginViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {

        loginVm.accountSessionLoading.observe(this, ::handleLoading)
        loginVm.accountSession.observe(this, ::handleAccountSessionCreation)
        loginVm.error.observe(this, ::handleError)
    }

    private fun handleLoading(show: Boolean) {
        binding.progress.isVisible = show
    }

    private fun handleError(error: Exception) = when (error) {
        is AccountExceptions.TooManyRequests -> {
            longToast(getString(R.string.too_many_requests))
        }

        else -> longToast(getString(R.string.ops_error))
    }

    private fun handleAccountSessionCreation(account: AccountEntity?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setUpViews() = with(binding) {

        registerText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            loginVm.createAccountSession(
                AccountEntity(
                    email = edtEmail.text.toString(),
                    password = edtPassword.text.toString()
                )
            )
        }
    }

}