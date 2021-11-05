package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var complaintArrayList : ArrayList<Complaint>
//    private lateinit var complaintAdapter : ComplaintAdapter
//    private lateinit var db: FirebaseFirestore
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        // For Add Complaint Button
//        addCompaintBtn.setOnClickListener {
//            startActivity(Intent(this, ResidentAddComplaintActivity::class.java))
//        }
//
//        recyclerView = findViewById(R.id.complaintViewRV)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
//
//        complaintArrayList = arrayListOf()
//
//        complaintAdapter = ComplaintAdapter(complaintArrayList)
//
//        recyclerView.adapter = complaintAdapter
//
//        EventChangeListener()
    }
//
//    private fun EventChangeListener() {
//        db = FirebaseFirestore.getInstance()
//        db.collection("complaints").
//        addSnapshotListener(object : EventListener<QuerySnapshot> {
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                if (error != null) {
//                    Log.e("Firestore Error", error.message.toString())
//                    return
//                }
//                for (dc : DocumentChange in value?.documentChanges!!) {
//                    if (dc.type == DocumentChange.Type.ADDED) {
//                        complaintArrayList.add(dc.document.toObject(Complaint::class.java))
//                    }
//                }
//                complaintAdapter.notifyDataSetChanged()
//            }
//        })
//    }
}