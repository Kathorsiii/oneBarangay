package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_appointment_display.*

class AppointmentDisplayActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_display)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Appointment Details"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val  = intent.getStringExtra("")
        val  = intent.getStringExtra("")
        val  = intent.getStringExtra("")
        val appointmentImgURL = intent.getStringArrayExtra("appointment_image")

        appointmentAccountNameText.text
        appointmentAccountTypeText.text
        appointmentStartDateText.text
        appointmentEndDateText.text
        documentTypeText.text
        documentPurposeText.text

        Picasso.get().load(appointmentImgURL).into(appointmentImage)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}