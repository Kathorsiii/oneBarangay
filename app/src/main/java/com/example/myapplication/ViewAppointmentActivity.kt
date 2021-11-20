package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityViewAppointmentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewAppointmentActivity : AppCompatActivity() {

    // ActionBar
    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityViewAppointmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentList: ArrayList<AppointmentItem>
    private lateinit var appointmentAdapter: AppointmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "View Appointment"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.addAppointmentBtn.setOnClickListener {
            val intent = Intent(this, ResidentReserveAppointmentActivity::class.java)
            startActivity(intent)
        }

        appointmentList = arrayListOf()

        recyclerView = binding.appointmentRV
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        appointmentAdapter = AppointmentAdapter(appointmentList
        ) { selectedAppointmentItem: AppointmentItem -> listItemClicked(selectedAppointmentItem) }

        recyclerView.adapter = appointmentAdapter

        getAppointment()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getAppointment() {
        val db = Firebase.firestore
        db.collection("document_request")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    appointmentList.add(document.toObject(AppointmentItem::class.java))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                appointmentAdapter.notifyDataSetChanged()
            }
    }

    private fun listItemClicked(appointment: AppointmentItem) {
        val documents = appointment.document!!
        val requestedDocuments: MutableList<String> = ArrayList()
        for (document in documents) {
            requestedDocuments.add(document["document_name"] as String)
        }

//        val startAppointment = appointment.start_appointment?.toDate().toString()
//        val endAppointment = appointment.end_appointment?.toDate().toString()
//        if (startAppointment != null && endAppointment != null) {
//            intent.putExtra("start_appointment", startAppointment)
//            intent.putExtra("end_appointment", endAppointment)
//        } else {
//            intent.putExtra("start_appointment", "Approve The Appointment First!")
//            intent.putExtra("end_appointment", "Approve The Appointment First!")
//        }

        val intent = Intent(this, AppointmentDisplayActivity::class.java)
        intent.putExtra("first_name", appointment.first_name)
        intent.putExtra("last_name", appointment.last_name)
        intent.putExtra("document_id", appointment.document_id)
        intent.putExtra("appointment_image", appointment.appointment_image)
        intent.putExtra("appointment_purpose", appointment.appointment_purpose)
        intent.putExtra("email", appointment.email)
        intent.putExtra("status", appointment.status)
        intent.putExtra("documents", requestedDocuments.joinToString(",\n"))

        // Working
        intent.putExtra("start_appointment", appointment.start_appointment?.toDate().toString())
        intent.putExtra("end_appointment", appointment.end_appointment?.toDate().toString())

        startActivity(intent)
    }
}