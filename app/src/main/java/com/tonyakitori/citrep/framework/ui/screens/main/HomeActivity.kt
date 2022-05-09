package com.tonyakitori.citrep.framework.ui.screens.main

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tonyakitori.citrep.R
import com.tonyakitori.citrep.databinding.ActivityHomeBinding
import com.tonyakitori.citrep.framework.utils.createInfoLog
import com.tonyakitori.citrep.framework.utils.longToast
import java.util.Date

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)

        handleDeepLinks()
    }

    private fun handleDeepLinks() {
        val data: Uri = intent?.data ?: return

        val userId = data.getQueryParameter("userId") ?: return
        val secret = data.getQueryParameter("secret") ?: return
        val expire = data.getQueryParameter("expire") ?: return

        userId.createInfoLog("QueryParams")
        secret.createInfoLog("QueryParams")
        expire.createInfoLog("QueryParams")


        if (Date((expire).toLong().times(1_000)).time >= Date().time) {
            val fragmentData = bundleOf(
                "userId" to userId,
                "secret" to secret
            )
            navController.navigate(R.id.navigation_profile, fragmentData)
        } else {
            longToast(getString(R.string.expired_deep_link))
        }
    }
}