package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_announcement_display.*

class AnnouncementDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_display)

        /**get Data*/
        val announcementIntent = intent
        val announcementTitle = announcementIntent.getStringExtra("title")
        val announcementDesc = announcementIntent.getStringExtra("desc")
        val announcementThumbnail = announcementIntent.getStringExtra("thumbnail")

        /**call text and images*/
//        title.text = announcementTitle
//        desc.text = announcementDesc
//        thumbnail.loadImage(announcementThumbnail, getProgessDrawable(this))
    }
}