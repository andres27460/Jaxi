package com.example.ProyectoFinalApp30Firebase.login.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ProyectoFinalApp30Firebase.driver.DriverActivity
import com.example.ProyectoFinalApp30Firebase.currentMail
import com.example.ProyectoFinalApp30Firebase.login.createLogin.RegisterDriverActivity
import com.example.ProyectoFinalApp30Firebase.login.createLogin.RegisterUserActivity

import com.example.ProyectoFinalApp30Firebase.user.PassengerActivity
import com.example.buttonnavigation.databinding.LoginFragmentBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

var flag = true
lateinit var currentUserName: String
lateinit var currentUserCC: String
lateinit var currentUserTravelsMade: String



var initFlag = true

lateinit var currentDriverName: String
lateinit var currentDriverCC: String
lateinit var currentDriverTravelsMade: String
lateinit var currentDriverCarPlate: String
lateinit var currentDriverCar: String
class LoginActivity :  AppCompatActivity() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var database : DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginFragmentBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference
        currentUserName = ""
        currentUserCC = ""
        currentUserTravelsMade = ""

        currentDriverName = ""
        currentDriverCC = ""
        currentDriverTravelsMade = ""
        currentDriverCarPlate = ""
        currentDriverCar = ""


        binding.button.setOnClickListener() {
            val mail = binding.editTextTextPersonName.text.toString()
            val pass = binding.editTextTextPersonName1.text.toString()
                if (mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(applicationContext,"Debe llenar el campo de Correo y Contraseña",Toast.LENGTH_SHORT).show()
                } else {
                    loginFirebaseAccount(mail, pass)
                }
        }
        binding.button16.setOnClickListener(){
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }
        binding.button17.setOnClickListener(){
            startActivity(Intent(this, RegisterDriverActivity::class.java))
        }
    }

    private fun loginFirebaseAccount(mail: String, pass: String) {
        auth.signInWithEmailAndPassword(mail, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Bienvenido")

                    currentMail = mail.replace(oldChar = '@', newChar = '0')
                    currentMail = currentMail.replace(oldChar = '.', newChar = '1')

                    findAccount(currentMail)

                   /* val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    finish()*/
                } else {
                    Toast.makeText(baseContext, "Correo o Contraseña son incorrectos.",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun findAccount(currentMail: String) {
        database = FirebaseDatabase.getInstance().getReference("Users/$currentMail")
        var getdataA = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(initFlag){
                for(i in p0.children) {
                    var tcc = i.child("ccUserValue").getValue()
                    var tname = i.child("nameUserString").getValue()
                    var tmade = i.child("travelsMadeUserValue").getValue()
                    if (tname != null) {
                        currentUserName = tname.toString()
                    }
                    if (tcc != null) {
                        currentUserCC = tcc.toString()
                    }
                    if (tmade != null) {
                        currentUserTravelsMade = tmade.toString()
                    }
                    if (!currentUserName.isNullOrEmpty() && !currentUserCC.isNullOrEmpty() && !currentUserTravelsMade.isNullOrEmpty()) {
                        flag = false
                        initFlag = false
                        initUserAccount()

                    }
                }
                    }
                }
            }
        database.addValueEventListener(getdataA)
        database = FirebaseDatabase.getInstance().getReference("Conductors/$currentMail")


        var getdataB = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(initFlag){
                for(i in p0.children){
                    var tcc = i.child("ccDriverValue").getValue()
                    var tname = i.child("nameDriverString").getValue()
                    var tmade = i.child("travelsMadeDriverValue").getValue()
                    var tCarPlate = i.child("carPlateDriverString").getValue()
                    var tCar = i.child("carDriverString").getValue()
                    if(tname != null){
                        currentDriverName = tname.toString()
                    }
                    if(tcc != null){
                        currentDriverCC = tcc.toString()
                    }
                    if(tmade != null){
                        currentDriverTravelsMade = tmade.toString()
                    }
                    if(tCarPlate != null){
                        currentDriverCarPlate = tCarPlate.toString()
                    }
                    if(tCar != null){
                        currentDriverCar = tCar.toString()
                    }
                    if(!currentDriverName.isNullOrEmpty() && !currentDriverCC.isNullOrEmpty() &&
                        !currentDriverTravelsMade.isNullOrEmpty() && !currentDriverCar.isNullOrEmpty() &&
                        !currentDriverCarPlate.isNullOrEmpty()){
                        flag = false
                        initFlag = false
                        initConductorAccount()

                    }

                }
                }
            }
        }
        database.addValueEventListener(getdataB)
    }

    private fun initConductorAccount() {

        database = FirebaseDatabase.getInstance().reference
        database.child("Conductors").child(currentMail).child("active").child("activeValue").setValue(1)
        val i = Intent(this, DriverActivity::class.java)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        finish()
    }

    private fun initUserAccount() {

        val i = Intent(this, PassengerActivity::class.java)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        finish()

    }
}