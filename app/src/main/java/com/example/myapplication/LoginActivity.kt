package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    // ViewBinding
    private lateinit var binding: ActivityLoginBinding

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

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        // ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        // Handle click, open sign up activity
        binding.signUpLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Handle click, begin login
        binding.loginBtn.setOnClickListener {
            // Before logging in, check credentials
            validateCredentials()
        }

    }

    private fun validateCredentials() {
        // Get data
        email = binding.emailLoginInput.text.toString().trim()
        password = binding.passwordLoginInput.text.toString().trim()

        // Validate credentials
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Invalid email format
            binding.emailLoginInput.setError("You have entered an invalid email format")
        } else if (TextUtils.isEmpty(password)) {
            // No password entered
            binding.passwordLoginInput.setError("Please enter password")
        } else {
            // Credentials are checked, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        // Show progress dialog
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Login success
                progressDialog.dismiss()
                // Get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged In as $email", Toast.LENGTH_SHORT).show()

                // Open Profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener { e->
                // Login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        // Check if user is already logged in
        // Get current user
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            // User is already logged in
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}