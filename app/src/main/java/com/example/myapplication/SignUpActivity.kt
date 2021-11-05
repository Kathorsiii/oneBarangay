package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    // ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

//        signInLinkBtn.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

        // ActionBar that has enabled back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Handle click, begin sign up
        binding.signupBtn.setOnClickListener {
            // Validate credentials
            validateCredentials()
        }

    }

    private fun validateCredentials() {
        // Get data
        email = binding.emailSignupInput.text.toString().trim()
        password = binding.pwdSignupInput.text.toString().trim()

        // Validate credentials
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Invalid email format
            binding.emailSignupInput.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            // No password entered
            binding.pwdSignupInput.error = "Please enter password"
        } else if (password.length < 8) {
            // Password length is less than 8
            binding.pwdSignupInput.error = "Password must be 8 characters long"
        }
        else {
            // Credentials are checked, begin sign up
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        // Show progress dialog
        progressDialog.show()

        // Create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Sign up success
                progressDialog.dismiss()

                // Get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                // Open Profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }

            .addOnFailureListener { e->
                // Sign up failed
                progressDialog.dismiss()
                Toast.makeText(this, "Sign Up failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Go back to teh previous activity once the back button of the action bar is clicked
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}