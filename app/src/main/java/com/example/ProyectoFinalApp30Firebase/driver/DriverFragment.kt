package com.example.ProyectoFinalApp30Firebase.driver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ProyectoFinalApp30Firebase.currentMail
import com.example.ProyectoFinalApp30Firebase.driver.options.DriverMapActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.*
import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.DriverFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DriverFragment : Fragment() {

    private lateinit var binding: DriverFragmentBinding
    private lateinit var database : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DriverFragmentBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference
        binding.textView7.setText(currentDriverName)
        binding.textView20.setText("CC: "+ currentDriverCC)
        binding.textView18.setText("Viajes reaizados: " + currentDriverTravelsMade)
        binding.textView16.setText("Vehiculo: "+ currentDriverCar)
        binding.textView17.setText("Placa: " + currentDriverCarPlate)
        binding.button6.setOnClickListener(){
            activity?.let{
                val intent = Intent (it, DriverMapActivity::class.java)
                it.startActivity(intent)
            }        }


        binding.button8.setOnClickListener(){
            activity?.let{
                database = FirebaseDatabase.getInstance().reference
                database.child("Conductors").child(currentMail).child("active").child("activeValue").setValue(0)
                val intent = Intent (it, LoginActivity::class.java)
                database = FirebaseDatabase.getInstance().reference
                it.startActivity(intent)
            }
        }
        return binding.root
    }


    }