package com.example.ProyectoFinalApp30Firebase.user


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ProyectoFinalApp30Firebase.login.Login.LoginActivity
import com.example.ProyectoFinalApp30Firebase.login.Login.currentUserCC
import com.example.ProyectoFinalApp30Firebase.login.Login.currentUserName
import com.example.ProyectoFinalApp30Firebase.login.Login.currentUserTravelsMade
import com.example.ProyectoFinalApp30Firebase.user.options.PassengerMapActivity

import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.PassengerFragmentBinding



class PassengerFragment : Fragment() {
    private lateinit var binding: PassengerFragmentBinding
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = PassengerFragmentBinding.inflate(inflater,container,false)
        binding.textNameView.setText(currentUserName)
        binding.textView21.setText("CC: "+ currentUserCC)
        binding.textView6.setText("Viajes reaizados: " + currentUserTravelsMade)
        binding.button6.setOnClickListener(){
            //findNavController().navigate(R.id.action_passengerFragment_to_passengerMapFragment)
            activity?.let{
                val intent = Intent (it, PassengerMapActivity::class.java)
                it.startActivity(intent)
            }
        }
        binding.button8.setOnClickListener(){
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }
        binding.buttonComment.setOnClickListener {
            findNavController().navigate(R.id.action_passengerFragment_to_commentsFragment)

        }
        return binding.root
    }

}