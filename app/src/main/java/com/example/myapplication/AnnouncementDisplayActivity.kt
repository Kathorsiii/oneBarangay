package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_announcement_display.*

class AnnouncementDisplayActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_display)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Announcement"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val announcementTitle = intent.getStringExtra("title")
        val announcementBody = intent.getStringExtra("body")
        val creationDate = intent.getStringExtra("creation_date")
        val announcementThumbnail = intent.getStringExtra("thumbnail")

        announcementDisplayTitle.text = announcementTitle
        announcementDisplayDesc.text = announcementBody
        announcementDisplayDate.text = creationDate

        Picasso.get().load(announcementThumbnail).into(announcementDisplayImage)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}