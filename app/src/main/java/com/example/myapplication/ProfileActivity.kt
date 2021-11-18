package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.success_dialog_update_user.view.*

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

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

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
            val db = Firebase.firestore

            val userID = firebaseAuth.uid!!

            // println(userID)

            // For viewing
            val viewUsers = db.collection("users").document(userID)
            viewUsers.get()
                .addOnSuccessListener { document ->
                    if (document != null) {

                        var firstName = document.getString("first_name")
                        var middleName = document.getString("middle_name")
                        var lastName = document.getString("last_name")

                        var fullName = "$firstName $middleName $lastName"

                        val imageProfileURL = document.getString("photo_url")

                        val profileImageView = findViewById<ImageView>(R.id.userProfileImage)

                        Picasso.get().load(imageProfileURL).into(profileImageView)

                        binding.nameUserProfile.setText(fullName)
                        binding.idTextInput.setText(document.getString("user_id"))
                        binding.roleUserInput.setText(document.getString("role"))
                        binding.emailAddressUser.setText(document.getString("email"))
                        binding.addressUserInput.setText(document.getString("address"))
                        binding.streetUserInput.setText(document.getString("street"))
                        binding.contactNumberUser.setText(document.getString("contact_number"))
//                    binding.ageUserInput.setText(document.getString("age"))
                        binding.birthPlaceUser.setText(document.getString("birth_place"))
                        binding.citizenshipUser.setText(document.getString("citizenship"))
                        binding.civilStatusUser.setText(document.getString("civil_status"))
                        binding.dateOfBirthUser.setText(document.getString("date_of_birth"))
                        binding.monthlyIncomeUser.setText(document.getString("monthly_income"))

                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

            // ====================================================== //

            binding.updateUserBtn.setOnClickListener {
                // For Update
                val updateUsers = db.collection("users").document(userID)
                    .update(mapOf(
                        "address" to binding.addressUserInput.text.toString(),
                        "contact_number" to binding.contactNumberUser.text.toString()
                    ))
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully updated!")
                        val view = View.inflate(this@ProfileActivity, R.layout.success_dialog_update_user, null)
                        val builder = AlertDialog.Builder(this@ProfileActivity)
                        builder.setView(view)

                        val dialog = builder.create()
                        dialog.show()

                        view.okay_update_btn.setOnClickListener {
                            dialog.dismiss()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        }
                    }
                    .addOnFailureListener {
                            e -> Log.w(TAG, "Error updating document", e)
                    }
            }

        } else {
            // If user is null, user if not logged in, go to Login Activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}