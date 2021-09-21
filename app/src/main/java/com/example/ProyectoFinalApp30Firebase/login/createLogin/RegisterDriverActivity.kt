package com.example.ProyectoFinalApp30Firebase.login.createLogin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.LoginActivity
import com.example.buttonnavigation.databinding.RegisterConductorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterDriverActivity : AppCompatActivity() {
    private lateinit var binding: RegisterConductorBinding
    private lateinit var auth: FirebaseAuth
    var database = FirebaseDatabase.getInstance().reference

    private lateinit var mail: String
    private lateinit var pass: String
    private lateinit var cc: String
    private lateinit var carKind: String
    private lateinit var carPlate: String
    private lateinit var driverName: String

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        binding = RegisterConductorBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.button5.setOnClickListener() {
            if (Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text.toString()).matches()) {
                if (binding.editTextTextPassword.text.toString() == binding.editTextTextPassword2.text.toString()) {
                    if (binding.editTextTextPassword.text.toString().length > 7) {
                        if(binding.editTextNumber2.text.toString().isNullOrEmpty() || binding.editTextNumber3.text.toString().isNullOrEmpty() ||
                            binding.editTextNumber4.text.toString().isNullOrEmpty() || binding.editTextNumber5.text.toString().isNullOrEmpty()){
                            binding.textView12.setText("Ingrese nombre, cedula, tipo de vehiculo y placa")

                        }else{
                            pass = binding.editTextTextPassword.text.toString()
                            mail = binding.editTextTextEmailAddress.text.toString()
                            cc = binding.editTextNumber2.text.toString()
                            carKind = binding.editTextNumber3.text.toString()
                            carPlate = binding.editTextNumber4.text.toString()
                            driverName = binding.editTextNumber5.text.toString()
                            createConductorFirebaseAccount(mail, pass, cc, carKind, carPlate, driverName)
                        }
                    } else {
                        binding.textView12.setText("La contraseña debe contener minimo 8 caracteres")
                    }
                    //  findNavController().navigate(R.id.action_register_fragment_to_login_Fragment)
                } else {
                    binding.textView12.setText("Contraseñas no coinciden, ingrese los datos nuevamente")
                }
            }
            else{
                binding.textView12.setText("Ingrese un correo valido")

            }
        }
    }

    private fun createConductorFirebaseAccount(mail: String, pass: String, cc: String, carKind: String, carPlate: String, driverName: String) {

        auth.createUserWithEmailAndPassword(mail, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Usuario creado con exito",
                        Toast.LENGTH_SHORT).show()
                    var tmail = mail.replace(oldChar = '@', newChar = '0')
                    tmail = tmail.replace(oldChar = '.', newChar = '1')
                    database.child("Conductors").child(tmail).child("availableDriverID").child("idDriverValue").setValue(0)
                    database.child("Conductors").child(tmail).child("CCDriver").child("ccDriverValue").setValue(cc)
                    database.child("Conductors").child(tmail).child("carDriver").child("carDriverString").setValue(carKind)
                    database.child("Conductors").child(tmail).child("carPlateDriver").child("carPlateDriverString").setValue(carPlate)
                    database.child("Conductors").child(tmail).child("travelsMadeDriver").child("travelsMadeDriverValue").setValue(0)
                    database.child("Conductors").child(tmail).child("nameDriver").child("nameDriverString").setValue(driverName)
                    database.child("Conductors").child(tmail).child("active").child("activeValue").setValue(0)
                    database.child("Conductors").child(tmail).child("active").child("latitudeValue").setValue(0)
                    database.child("Conductors").child(tmail).child("active").child("longitudeValue").setValue(0)

                    database.child("Conductors").child(tmail).child("service").child("availableService").setValue(0)
                    database.child("Conductors").child(tmail).child("service").child("serviceLatitudeIn").setValue(0)
                    database.child("Conductors").child(tmail).child("service").child("serviceLongitudeIn").setValue(0)
                    database.child("Conductors").child(tmail).child("service").child("serviceLatitudeOut").setValue(0)
                    database.child("Conductors").child(tmail).child("service").child("serviceLongitudeOut").setValue(0)


                    database.child("Comments").child(tmail).child("numberComments").child("numberCommentsValue").setValue(0)
                    database.child("Comments").child(tmail).child("CC").child("ccValue").setValue(cc)
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