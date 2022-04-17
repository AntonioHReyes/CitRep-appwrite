package com.tonyakitori.citrep.framework.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tonyakitori.citrep.databinding.ActivityLoginBinding
import com.tonyakitori.citrep.domain.entities.AccountEntity
import com.tonyakitori.citrep.framework.helpers.AppWriteClientManager
import com.tonyakitori.citrep.framework.signup.SignUpActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val appWriteClient: AppWriteClientManager by inject()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }


    private fun setUpViews() = with(binding) {

        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        loginButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    appWriteClient.creteAccountSession(
                        AccountEntity(
                            email = edtEmail.text.toString(),
                            password = edtPassword.text.toString()
                        )
                    )
                    Toast.makeText(this@LoginActivity, "Exito!!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("ErrorSession", e.toString())
                    Toast.makeText(this@LoginActivity, "Ops! ocurrio un error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}