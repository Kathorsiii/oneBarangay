package com.example.myapplication.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.data.model.FirestoreRBI
import com.example.myapplication.databinding.ActivityViewOcrBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_ocr.*

class ViewOcrActivity : AppCompatActivity() {
    private lateinit var actionBar: ActionBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityViewOcrBinding
    private lateinit var rbiList: ArrayList<FirestoreRBI>
    private lateinit var rbiAdapter: RbiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOcrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "View RBI"

        // Back button
        // actionBar.setDisplayHomeAsUpEnabled(true)

        goBackHmBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        rbiList = arrayListOf()

        recyclerView = binding.rvRbi
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        rbiAdapter = RbiAdapter(rbiList)
        recyclerView.adapter = rbiAdapter
        getRBI()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }

    private fun getRBI() {
        val db = Firebase.firestore
        db.collection("rbi")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    rbiList.add(document.toObject(FirestoreRBI::class.java))
                }
                rbiAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Error", "RBI Get due to ${e.message}")
            }
    }
}