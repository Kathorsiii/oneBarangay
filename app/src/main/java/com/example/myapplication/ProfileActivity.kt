package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // Handle click logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        // Check user if logged in or not
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            // If user is not null, user is logged in, get the user info
            val email = firebaseUser.email
            // Set to text view
            binding.emailTV.text = email
            readFireStoreData()

        } else {
            // If user is null, user if not logged in, go to Login Activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()

                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("first_name")).append(" ")
                            .append(document.data.getValue("last_name")).append("\n")
                    }
                    textViewResult.setText(result)
                }
            }
    }
}