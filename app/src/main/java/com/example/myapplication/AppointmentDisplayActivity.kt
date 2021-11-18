package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAppointmentDisplayBinding
import com.squareup.picasso.Picasso

class AppointmentDisplayActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityAppointmentDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentDisplayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Appointment Details"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val firstName = intent.getStringExtra("first_name")
        val lastName = intent.getStringExtra("last_name")
        val appointmentImage = intent.getStringExtra("appointment_image")
        val appointmentPurpose = intent.getStringExtra("appointment_purpose")
        val startAppointment = intent.getStringExtra("start_appointment")
        val endAppointment = intent.getStringExtra("end_appointment")
        val status = intent.getStringExtra("status")
        val documents = intent.getStringExtra("documents")

        binding.appointmentAccountNameText.text = "$firstName $lastName"
        binding.appointmentDocumentStatus.text = status
        binding.documentTypeText.text = documents
        binding.documentPurposeText.text = appointmentPurpose
        binding.appointmentStartDateText.text = startAppointment
        binding.appointmentEndDateText.text = endAppointment

        Picasso.get()
            .load(appointmentImage)
            .into(binding.appointmentImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}