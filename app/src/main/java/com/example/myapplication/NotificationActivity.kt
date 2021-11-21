package com.example.myapplication

import android.app.NotificationManager
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_notification.*
import retrofit2.Response
import retrofit2.Retrofit

class NotificationActivity : AppCompatActivity() {

    var CHANNEL_ID = "CHANNEL"

    lateinit var manager: NotificationManager

    private lateinit var notificationBody: NotificationBody

    // Notification
    var title = ""
    var message = ""

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Notification"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!

        notifBtn.setOnClickListener {

            sendNotifications()

        }

    }

    private fun sendNotifications() {

        val firebaseUser = firebaseAuth.currentUser

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!

        val notifService = NotificationRetrofitInstance
            .getRetrofitInstance()
            .create(NotificationRetrofitInterface::class.java)

//        val response = notifService.sendNotification(NotificationBody)

//        val responseLiveData: LiveData<Response<NotificationBody>> = liveData {
//            val response = notifService.sendNotification(NotificationBody("Complaint Added Successfully",
//                "You have successfully added a complaint",
//                "ftGCuYHCQoOU09WKNJ3TXM:APA91bGGtT-pWf4I71kwuF3QIW15Jrctdy7ypdLAG3RmFSsPTAoVmQ3aTRKf9PcPwF2w3NyrKLyD_aKccr9YhMuXVUyqdxNh05qOewJpNu75BTYoqC_wF2UCXhm2qFqfsqkKfvFbKQ1J"))
//            emit(response)
        val responseLiveData: LiveData<Response<NotificationBody>> = liveData {
            val response =
                notifService.sendNotification(NotificationBody("Title Notification Activity",
                    "Message Notification Activity",
                    "ftGCuYHCQoOU09WKNJ3TXM:APA91bGGtT-pWf4I71kwuF3QIW15Jrctdy7ypdLAG3RmFSsPTAoVmQ3aTRKf9PcPwF2w3NyrKLyD_aKccr9YhMuXVUyqdxNh05qOewJpNu75BTYoqC_wF2UCXhm2qFqfsqkKfvFbKQ1J"))
            emit(response)
        }

        responseLiveData.observe(this, {
            notificationBody = it.body()!!
//            println(notificationBody)
        })

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result!!

            // Log and toast
//            val generatedToken = getString(R.string.msg_token_fmt, token)
//            Log.d(TAG, generatedToken)
//            Toast.makeText(baseContext, generatedToken, Toast.LENGTH_SHORT).show()

//            println(generatedToken)

            db.collection("users").document(userID)
                .set(mapOf(
                    "mobile_notification" to token,
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}