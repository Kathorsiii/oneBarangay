package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_view_appointment.*

class ViewAppointmentActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_appointment)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "View Appointment"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        addAppointmentBtn.setOnClickListener {
            val intent = Intent(this, ResidentReserveAppointmentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}