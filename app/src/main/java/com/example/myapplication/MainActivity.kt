package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var userToken: String

    // Notification
    var title = ""
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        announcementBtn.setOnClickListener {
            val intent = Intent(this, AnnouncementActivity::class.java)
            startActivity(intent)
        }

        dataVizBtn.setOnClickListener {
            val intent = Intent(this, DataVisualizationActivity::class.java)
            startActivity(intent)
        }

        servicesBtn.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            startActivity(intent)
        }

//        notificationBtn.setOnClickListener {
//            val intent = Intent(this, NotificationActivity::class.java)
//            startActivity(intent)
//        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!

        // For new token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            userToken = task.result!!

            // Log and toast
//            val generatedToken = getString(R.string.msg_token_fmt, userToken)
//            Log.d(TAG, generatedToken)
//            Toast.makeText(baseContext, generatedToken, Toast.LENGTH_SHORT).show()
////
//            println(generatedToken)

            db.collection("users").document(userID)
                .set(mapOf(
                    "mobile_notification" to userToken,
                ), SetOptions.merge())

            db.collection("users").document(userID)
                .collection("notification")
                .add(mapOf(
                    "title" to title,
                    "body" to message,
                    "icon" to "https://storage.googleapis.com/onebarangay-malanday/assets/img/favicon/favicon.ico",
                    "requireInteraction" to true
                ))
        })
    }
}