package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class AnnouncementActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Announcement"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Announcement Module
//        val arrayList = ArrayList<Model>()
//
//        arrayList.add(Model("Announcement 1", "Mon, 05 July 2021", "09:00 - 11:00 AM", "Announcement 1 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 2", "Mon, 06 July 2021", "09:00 - 11:00 AM", "Announcement 2 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 3", "Mon, 07 July 2021", "09:00 - 11:00 AM", "Announcement 3 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 4", "Mon, 08 July 2021", "09:00 - 11:00 AM", "Announcement 4 Description", R.drawable.dice))
//        arrayList.add(Model("Announcement 5", "Mon, 09 July 2021", "09:00 - 11:00 AM", "Announcement 5 Description", R.drawable.dice))
//
//        val announcementAdapter = AnnouncementAdapter(arrayList, this)
//
//        rv_announcement.layoutManager = LinearLayoutManager(this)
//        rv_announcement.adapter = announcementAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}