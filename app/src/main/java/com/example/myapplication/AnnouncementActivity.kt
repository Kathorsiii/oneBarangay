package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnnouncementActivity : AppCompatActivity() {

    // For Firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var announcementList: ArrayList<AnnouncementData>
    private lateinit var announcementAdapter: AnnouncementAdapter
    private lateinit var db: FirebaseFirestore

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

        // For Firestore
        recyclerView = findViewById(R.id.rv_announcement)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        announcementList = arrayListOf()

        announcementAdapter = AnnouncementAdapter(announcementList,
            { selectedAnnouncementItem: AnnouncementData -> listItemClicked(selectedAnnouncementItem) })

        recyclerView.adapter = announcementAdapter

        getAnnouncementData()
    }

    private fun getAnnouncementData() {
        val db = Firebase.firestore
        db.collection("announcements")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {

                    announcementList.add(document.toObject(AnnouncementData::class.java))
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                announcementAdapter.notifyDataSetChanged()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun listItemClicked(announcement: AnnouncementData) {
//        Toast.makeText(this,
//            "Supplier name is ${announcement.title}", Toast.LENGTH_LONG).show()

        val intent = Intent(this, AnnouncementDisplayActivity::class.java)
        intent.putExtra("title", announcement.title)
        intent.putExtra("body", announcement.body)
        intent.putExtra("creation_date", announcement.creation_date?.toDate().toString())
        intent.putExtra("thumbnail", announcement.thumbnail)
        startActivity(intent)
    }
}