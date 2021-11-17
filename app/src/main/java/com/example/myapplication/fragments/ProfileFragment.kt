package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */

class ProfileFragment : Fragment() {

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var email: TextView
    private lateinit var contactNumber: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            firebaseAuth.signOut()
            activity?.finish()
        }
    }

//    private fun readUserData() {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("users")
//            .get()
//            .addOnCompleteListener {
//                val result: StringBuffer = StringBuffer()
//
//                if (it.isSuccessful) {
//                    for (document in it.result!!) {
//                        result.append(document.data.getValue("email")).append(" ")
//                            .append(document.data.getValue("contact_number")).append("\n")
//
//                    }
//                    emailTV.setText(result)
//                }
//            }
//    }

    override fun onStart() {
        super.onStart()

    }

}