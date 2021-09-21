package com.example.ProyectoFinalApp30Firebase.login.createLogin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.LoginActivity
import com.example.buttonnavigation.databinding.RegisterUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase



class RegisterUserActivity : AppCompatActivity()  {

    private lateinit var Register_fragmentBinding: RegisterUserBinding
    private lateinit var auth: FirebaseAuth
    var database = FirebaseDatabase.getInstance().reference

    private lateinit var mail: String
    private lateinit var pass: String
    private lateinit var usercc: String
    private lateinit var userName: String


    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        Register_fragmentBinding = RegisterUserBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(Register_fragmentBinding.root)

        Register_fragmentBinding.button5.setOnClickListener() {
            if (Patterns.EMAIL_ADDRESS.matcher(Register_fragmentBinding.editTextTextEmailAddress.text.toString()).matches()) {
                if (Register_fragmentBinding.editTextTextPassword.text.toString() == Register_fragmentBinding.editTextTextPassword2.text.toString()) {
                    if (Register_fragmentBinding.editTextTextPassword.text.toString().length > 7) {
                        if(Register_fragmentBinding.editTextNumber2.text.toString().isNullOrEmpty() || Register_fragmentBinding.editName.text.toString().isNullOrEmpty()){
                            Register_fragmentBinding.textView12.setText("Ingrese nombre y cedula")

                        }
                        else {
                            pass = Register_fragmentBinding.editTextTextPassword.text.toString()
                            mail = Register_fragmentBinding.editTextTextEmailAddress.text.toString()
                            usercc = Register_fragmentBinding.editTextNumber2.text.toString()
                            userName = Register_fragmentBinding.editName.text.toString()
                            createUserFirebaseAccount(mail, pass, usercc, userName)
                        }
                    } else {
                        Register_fragmentBinding.textView12.setText("La contraseña debe contener minimo 8 caracteres")
                    }
                    //  findNavController().navigate(R.id.action_register_fragment_to_login_Fragment)
                } else {
                    Register_fragmentBinding.textView12.setText("Contraseñas no coinciden, ingrese los datos nuevamente")
                }
            }
            else{
                Register_fragmentBinding.textView12.setText("Ingrese un correo valido")

            }
        }
    }

    private fun createUserFirebaseAccount(mail: String, pass: String, usercc: String, userName: String) {

        auth.createUserWithEmailAndPassword(mail, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Usuario creado con exito",
                        Toast.LENGTH_SHORT).show()
                    var tmail = mail.replace(oldChar = '@', newChar = '0')
                    tmail = tmail.replace(oldChar = '.', newChar = '1')
                    database.child("Users").child(tmail).child("availableUserID").child("idUserValue").setValue(0)
                    database.child("Users").child(tmail).child("ccUser").child("ccUserValue").setValue(usercc)
                    database.child("Users").child(tmail).child("travelsMadeUser").child("travelsMadeUserValue").setValue(0)
                    database.child("Users").child(tmail).child("nameUser").child("nameUserString").setValue(userName)
                   // currentUserName = userName
                   // currentUserCC = usercc
                    startActivity(Intent(this, LoginActivity::class.java))


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Usuario ya existe",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }

    }


}