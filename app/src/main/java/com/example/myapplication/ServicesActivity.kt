package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_services.*

class ServicesActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Services"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        viewAppointmentBtn.setOnClickListener {
            val intent = Intent(this, ViewAppointmentActivity::class.java)
            startActivity(intent)
        }

        viewReportsBtn.setOnClickListener {
            val intent = Intent(this, ResidentViewComplaintActivity::class.java)
            startActivity(intent)
        }

        bulkSchedBtn.setOnClickListener {
            val intent = Intent(this, AddBulkScheduleActivity::class.java)
            startActivity(intent)
        }

        scanDocuBtn.setOnClickListener {
            val intent = Intent(this, ScanDocumentActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}