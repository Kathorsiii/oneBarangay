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

        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")
        val thumbnail = intent.getStringExtra("thumbnail")

        announcementDisplayTitle.text = title
        announcementDisplayDesc.text = body
        Picasso.get().load(thumbnail).into(announcementDisplayImage)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}