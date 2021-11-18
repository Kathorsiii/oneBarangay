package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewComplaintActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var complaintList: ArrayList<ComplaintData>
    private lateinit var complaintAdapter: ComplaintAdapter
//    private lateinit var db: FirebaseFirestore

    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_complaint)

        // ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Complaint"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

//        addComplaintBtn.setOnClickListener {
//            val intent = Intent(this, ResidentAddComplaintActivity::class.java)
//            startActivity(intent)
//        }

        recyclerView = findViewById(R.id.complaintViewRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        complaintList = arrayListOf()

        complaintAdapter = ComplaintAdapter(complaintList,
            { selectedComplaintItem: ComplaintData -> listItemClicked(selectedComplaintItem) })

        getComplaintData()
    }

    private fun getComplaintData() {
        val db = Firebase.firestore
        db.collection("complaints")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    complaintList.add(document.toObject(ComplaintData::class.java))
                    println(complaintList)
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                complaintAdapter.notifyDataSetChanged()
            }
    }

    private fun listItemClicked(complaint: ComplaintData) {
        Toast.makeText(this,
            "Supplier name is ${complaint.complainant_name}", Toast.LENGTH_LONG).show()

//        val intent = Intent(this, AnnouncementDisplayActivity::class.java)
//        intent.putExtra("title", announcement.title)
//        intent.putExtra("body", announcement.body)
////        intent.putExtra("creation_date", announcement.creation_date).toString()
//        intent.putExtra("thumbnail", announcement.thumbnail)
//        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}