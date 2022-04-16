package com.tonyakitori.citrep.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tonyakitori.citrep.databinding.LoginActivityBinding
import com.tonyakitori.citrep.framework.helpers.AppWriteClientManager
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val appWriteClient: AppWriteClientManager by inject()

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}