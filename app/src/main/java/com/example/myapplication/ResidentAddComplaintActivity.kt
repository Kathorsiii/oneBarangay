package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_resident_add_complaint.*

class ResidentAddComplaintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_add_complaint)

        uploadProofImage.setOnClickListener {
            startActivity(Intent(this, ResidentAddComplaintActivity::class.java))
        }
    }
}