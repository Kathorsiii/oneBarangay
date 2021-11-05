package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_resident_add_complaint.*

class ResidentAddComplaintActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_add_complaint)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Add Complaint"

        uploadProofImage.setOnClickListener {
            startActivity(Intent(this, ResidentUploadComplaintProofActivity::class.java))
        }
    }
}