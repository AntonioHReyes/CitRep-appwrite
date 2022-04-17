package com.tonyakitori.citrep.framework.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tonyakitori.citrep.databinding.ActivitySignUpBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.helpers.AppWriteClientManager
import com.tonyakitori.citrep.framework.login.LoginActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignUpActivity : AppCompatActivity() {

    private val appWriteClient: AppWriteClientManager by inject()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }


    private fun setUpViews() = with(binding) {

        loginText.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
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

        lifecycleScope.launch {
            try {
                appWriteClient.createAccount(
                    AccountEntity(
                        email = edtEmail.text.toString(),
                        password = edtPassword.text.toString(),
                        name = edtName.text.toString()
                    )
                )

                Toast.makeText(
                    this@SignUpActivity,
                    "El registro ha sido exitoso",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@SignUpActivity, "Ops! ocurrio un error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateForm(): Boolean = with(binding) {
        return edtEmail.text.toString().isNotBlank()
                && edtName.text.toString().isNotBlank()
                && edtPassword.text.toString().isNotBlank()
                && edtPassword.text.toString() == edtConfirmPassword.text.toString()
    }
}