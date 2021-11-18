package com.example.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityAnnouncementBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnnouncementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnnouncementBinding

    // For Firestore
    private lateinit var recyclerView: RecyclerView
//    private lateinit var announcementArrayList: ArrayList<AnnouncementDataClass>

    //    private lateinit var announcementAdapter: AnnouncementAdapter
    private lateinit var db: FirebaseFirestore

    // ActionBar
    private lateinit var actionBar: ActionBar

    // New
//    private lateinit var announcementList: MutableList<AnnouncementData>
    private lateinit var announcementAdapter: AnnouncementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_announcement)

        binding = ActivityAnnouncementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Announcement"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // For Firestore
//        recyclerView = findViewById(R.id.rv_announcement)
        recyclerView = binding.rvAnnouncement
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

//        announcementArrayList = arrayListOf()

//        announcementAdapter = AnnouncementAdapter(announcementArrayList)

//        announcementList = getAnnouncementData()

//        println(getAnnouncementData())

//        recyclerView.adapter = AnnouncementAdapter(announcementList,
//            { selectedAnnouncementItem: AnnouncementData -> listItemClicked(selectedAnnouncementItem) })

//        EventChangeListener()
    }

    private fun listItemClicked(announcement: AnnouncementData) {
        Toast.makeText(this,
            "${announcement.announcement_id} -> ${announcement.title}",
            Toast.LENGTH_SHORT).show()
    }

    //    New
    private fun getAnnouncementData(): MutableList<String> {
        val announcementDocs: MutableList<String> = ArrayList()

        val db = Firebase.firestore
        db.collection("announcements")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val announcement_id = document.getString("announcement_id")!!
                    val title = document.getString("title")!!
                    val desc = document.getString("body")!!
                    val thumbnail = document.getString("thumbnail")!!

                    announcementDocs.add(announcement_id)
//                    println(announcement_id)
//                    println(announcementList)
//                    Log.d(TAG, "${document.id} => ${document.data}")

                }
            }

        return announcementDocs

//            .addOnFailureListener { exception ->
//                Log.d(TAG, "Error getting documents: ", exception)
//            }

//        return announcementDocs

    }

//    private fun EventChangeListener() {
//
//        db = FirebaseFirestore.getInstance()
//        db.collection("announcements").addSnapshotListener(object : EventListener<QuerySnapshot> {
//            override fun onEvent(
//                value: QuerySnapshot?,
//                error: FirebaseFirestoreException?,
//            ) {
//                if (error != null) {
//                    Log.e("Firestore Error", error.message.toString())
//                    return
//                }
//
//                for (dc: DocumentChange in value?.documentChanges!!) {
//                    if (dc.type == DocumentChange.Type.ADDED) {
//                        announcementArrayList.add(dc.document.toObject(AnnouncementDataClass::class.java))
//                    }
//                }
//                announcementAdapter.notifyDataSetChanged()
//            }
//        })
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}