package com.example.ProyectoFinalApp30Firebase.user.options


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buttonnavigation.R
import com.example.buttonnavigation.databinding.CommentsAddFragmentBinding
import com.google.firebase.database.FirebaseDatabase

class CommentsAddFragment() : Fragment() {

    private lateinit var binding: CommentsAddFragmentBinding
    var database = FirebaseDatabase.getInstance().reference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CommentsAddFragmentBinding.inflate(inflater,container,false)


        binding.buttonUpComment.setOnClickListener(){
            var comment = binding.editUpCommentText.getText().toString()
            database.child("Comments").child(llave).child("comments").child(numComments).setValue(comment)
            var aumentComment = (numComments.toInt() + 1).toString()
            database.child("Comments").child(llave).child("numberComments").child("numberCommentsValue").setValue(aumentComment)
            Toast.makeText(requireContext(),"Comentario almacenado: $comment", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_commentsAddFragment_to_commentsFragment)


        }
        return binding.root

    }

}