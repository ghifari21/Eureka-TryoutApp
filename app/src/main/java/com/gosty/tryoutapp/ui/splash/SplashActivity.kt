package com.gosty.tryoutapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.ui.auth.AuthActivity
import com.gosty.tryoutapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        GlobalScope.launch {
            delay(3000)
            if (auth.currentUser != null) {
                startActivity(
                    Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                )
            } else {
                startActivity(
                    Intent(
                        this@SplashActivity,
                        AuthActivity::class.java
                    )
                )
            }
            finish()
        }
    }
}