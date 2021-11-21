package com.example.myapplication.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ViewComplaintActivity
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.api.RetrofitInterface
import com.example.myapplication.data.model.ocr.FamilyItem
import com.example.myapplication.data.model.ocr.HouseData
import com.example.myapplication.data.model.ocr.RBI
import com.example.myapplication.databinding.ActivityOcrResultBinding
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ocr_result.*
import kotlinx.android.synthetic.main.success_dialog_add_complaint.view.*
import retrofit2.Response

class OcrResultActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityOcrResultBinding
    private lateinit var houseData: List<HouseData>
    private lateinit var familyData: List<FamilyItem>

    private lateinit var recyclerView: RecyclerView
    private lateinit var ocrAdapter: OcrAdapter


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOcrResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "OCR Scan Result"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
//        val filename = extras!!.getString("filename")!!
        // For Testing
        val filename = "3.jpg"
//        val filename = extras!!.getString("filename")!!
        val filename = "3.jpg"

        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(RetrofitInterface::class.java)
        val responseLiveData: LiveData<Response<RBI>> = liveData {
            val response = retService.getOcr(filename)
            emit(response)
        }

        responseLiveData.observe(this, {
            if (it.isSuccessful) {
                try {
                    val ocr = it.body()!!
                    houseData = ocr.house_data
                    familyData = ocr.family_data

                    println(houseData)

                    ocrAdapter = OcrAdapter(familyData
                    ) { selectedFamilyItem: FamilyItem ->
                        listItemClicked(selectedFamilyItem)
                    }
                    recyclerView = binding.ocrRecyclerView
                    recyclerView.adapter = ocrAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.setHasFixedSize(true)

                    ocrAdapter.notifyDataSetChanged()
                } catch (e: Error) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
//                initOcrResult()

            } else {
                println(it.errorBody())
            }
        })

        submitBtnOCR.setOnClickListener {
            familyData.forEachIndexed { index, familyItem ->
                println("Family Member $index -> $familyItem")
            }

            val db = Firebase.firestore
            val houseNum = houseData[0].house_num.text

            val houseRef = db.collection("rbi").document(houseNum)

            familyData.forEach { familyItem ->
                houseRef.collection("family")
                    .add(familyItem)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                        val dialogView =
                            View.inflate(this, R.layout.success_dialog_add_complaint, null)
                        val builder = AlertDialog.Builder(this)
                        builder.setView(dialogView)

                        val dialog = builder.create()
                        dialogView.dialog_message.text = "RBI has successfully been added!"
                        dialog.show()

                        dialogView.complaintOkBtn.setOnClickListener {
                            dialog.dismiss()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                            val intent = Intent(this, ViewOcrActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error writing document $e", Toast.LENGTH_LONG).show()
                    }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun listItemClicked(family: FamilyItem) {
        Toast.makeText(
            this,
            family.first_name.text,
            Toast.LENGTH_LONG
        ).show()
    }
}