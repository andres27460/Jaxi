package com.example.ProyectoFinalApp30Firebase.user.options

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.CommentsFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

lateinit var llave: String
lateinit var numComments: String
class CommentsFragment : Fragment() {

    private lateinit var binding: CommentsFragmentBinding
    var database = FirebaseDatabase.getInstance().reference
    var tcflag = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = CommentsFragmentBinding.inflate(inflater,container,false)
        binding.button10.setOnClickListener(){
           findNavController().navigate(R.id.action_commentsFragment_to_passengerFragment)
        }

        database = FirebaseDatabase.getInstance().getReference("Comments")



        var getdataB = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                    for(i in p0.children){
                         llave  = i.key.toString()

                        numComments = i.child("numberComments").value.toString()


                        if(numComments != "null"){
                            numComments = numComments.replace("[^0-9]".toRegex(), "")
                            val alertDialog: AlertDialog? = let{
                                val builder = AlertDialog.Builder(requireContext())
                                builder.apply{
                                    setTitle("Comentarios")
                                    setMessage("Desea comentar al conductor $llave ?")
                                    setPositiveButton("Si"){ dialog, id ->
                                        findNavController().navigate(R.id.action_commentsFragment_to_commentsAddFragment)


                                    }
                                    setNegativeButton("No"){ dialog, id ->


                                    }
                                }
                                builder.create()
                            }
                            alertDialog?.show()


                        }

                }
            }
        }


        return binding.root
    }


}