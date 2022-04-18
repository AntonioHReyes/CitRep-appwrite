package com.tonyakitori.citrep.framework.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.ActivityLoginBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.main.MainActivity
import com.tonyakitori.citrep.framework.signup.SignUpActivity
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
        loginVm.accountSession.observe(this, ::handleAccountCreation)
        loginVm.error.observe(this) { longToast(getString(R.string.ops_error)) }
    }

    private fun handleLoading(show: Boolean) {
        binding.progress.isVisible = show
    }

    private fun handleAccountCreation(account: AccountEntity) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setUpViews() = with(binding) {

        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
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