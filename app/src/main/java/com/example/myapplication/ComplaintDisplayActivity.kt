package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_complaint_display.*

class ComplaintDisplayActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_display)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Complaint Details"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val complainantName = intent.getStringExtra("complainant_name")
        val complaintType = intent.getStringExtra("complaint_type")
        val complaintComment = intent.getStringExtra("comment")
        val complainantContactNumber = intent.getStringExtra("contact_number")
        val complaintDate = intent.getStringExtra("complaint_date")

        println(complaintDate)
        val imageUrl = intent.getStringExtra("image_url")

        complainantNameText.text = complainantName
        complaintTypeText.text = complaintType
        complaintCommentText.text = complaintComment
        contactNumberText.text = complainantContactNumber
        complaintDateText.text = complaintDate

        Picasso.get().load(imageUrl).into(complaintDisplayImg)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}