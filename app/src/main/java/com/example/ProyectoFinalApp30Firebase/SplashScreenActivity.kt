package com.example.ProyectoFinalApp30Firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ProyectoFinalApp30Firebase.driver.DriverActivity
import com.example.ProyectoFinalApp30Firebase.driver.options.DriverMapActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.LoginActivity
import com.example.ProyectoFinalApp30Firebase.user.PassengerActivity
import com.example.ProyectoFinalApp30Firebase.user.options.PassengerMapActivity

import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 0)

    }
}