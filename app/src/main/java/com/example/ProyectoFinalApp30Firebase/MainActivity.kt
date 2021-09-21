package com.example.ProyectoFinalApp30Firebase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.buttonnavigation.R



lateinit var currentMail: String
lateinit var driverActiveFlag: String
lateinit var passengerActiveFlag: String

var latitud = ""
var longitud = ""
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

       // val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       // val appBarConfiguration = AppBarConfiguration(
         //   setOf(
           //     R.id.register_fragment, R.id.login_Fragment
            //)
        //)
       // setupActionBarWithNavController(navController, appBarConfiguration)
       // navView.setupWithNavController(navController)

    }



}