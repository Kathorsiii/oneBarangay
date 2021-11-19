package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivityResidentAddComplaintBinding
import kotlinx.android.synthetic.main.activity_resident_add_complaint.*
import android.widget.AutoCompleteTextView
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import kotlinx.android.synthetic.main.success_dialog_add_complaint.view.*
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.hashMapOf as hashMapOf


class ResidentAddComplaintActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityResidentAddComplaintBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var complaintYear: Int = 0
    private var complaintMonth: Int = 0
    private var complaintDay: Int = 0
    private var complaintHour: Int = 0
    private var complaintMinute: Int = 0

    private lateinit var complainantName: String
    private lateinit var email: String
    private lateinit var address: String
    private lateinit var contactNumber: String

    private lateinit var userData: Map<String, Any>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_resident_add_complaint)

        binding = ActivityResidentAddComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Add Complaint"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        uploadComplaintProofImage.setOnClickListener {
            startActivity(Intent(this, ResidentUploadComplaintProofActivity::class.java))
        }

        // Complaint Cancel Button
        complaintCancelBtn.setOnClickListener {
            startActivity(Intent(this, ResidentViewComplaintActivity::class.java))
        }

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!
        // Toast.makeText(this, "User ID: ${userID}", Toast.LENGTH_LONG).show()
        // println(userID)

        // For Complaint Type Dropdown
        val complaintList = resources.getStringArray(R.array.complaint_list)
        val complaintTypeAdapter = ArrayAdapter(this, R.layout.complaint_list_item, complaintList)
        binding.complaintTypeInput.setAdapter(complaintTypeAdapter)

        // For Complaint Status Dropdown
        val complaintStatus = resources.getStringArray(R.array.complaint_status_list)
        val complaintStatusAdapter = ArrayAdapter(this, R.layout.complaint_status_list_item, complaintStatus)
        binding.complaintStatusInput.setAdapter(complaintStatusAdapter)

        var complaintSelectedValue: String = ""
        var complaintStatusValue: String = ""

        // For Complaint Type Dropdown
        (complaintTypeInput as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                complaintSelectedValue = complaintTypeAdapter.getItem(position)!!

                // Toast.makeText(this, "Complaint Type: ${complaintSelectedValue}", Toast.LENGTH_LONG).show()
            }

        // For Complaint Status Dropdown
        (complaintStatusInput as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                complaintStatusValue = complaintStatusAdapter.getItem(position)!!

                // Toast.makeText(this, "Complaint Status: ${complaintStatusValue}", Toast.LENGTH_LONG).show()
            }

        dateOfIncident()

        val userRef = db.collection("users").document(userID)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    userData = document.data!!

                    // println(userData)
                    var firstName = userData["first_name"]
                    var lastName = userData["last_name"]
                    complainantName = "$firstName $lastName"

                    email = userData["email"] as String
                    contactNumber = userData["contact_number"] as String
                    address = userData["address"] as String

                    binding.complainantNameInput.setText(complainantName)
                    binding.complainantEmailInput.setText(email)
                    binding.complainantNumberInput.setText(contactNumber)
                    binding.complainantAddressInput.setText(address)

                }
            }

        complaintDoneBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(
                complaintYear,
                complaintMonth,
                complaintDay,
                complaintHour,
                complaintMinute
            )

            var complainantHouseNum = complainantHouseNumInput.text.toString()
            var complainantNameText = complainantNameInput.text.toString()
            var complainantNumber = complainantNumberInput.text.toString()
            var complainantAddress = complainantAddressInput.text.toString()
            var complaintReason = complaintReasonInput.text.toString()
            var complaintDate = complaintDateIncident.text
            var complaintTime = complaintTimeIncident.text.toString()

//            Toast.makeText(this,
//                "Complainant Name: ${complainantNameText}" +
//                        "\nComplainant House Number: ${complainantHouseNum}" +
//                        "\nPhone Number: ${complainantNumber}" +
//                        "\nDate of Incident: ${complaintIncidentDate}" +
//                        "\nTime of Incident: ${complaintTime}" +
//                        "\nComplainant Address: ${complainantAddress}" +
//                        "\nComplainant Reason: ${complaintReason}" +
//                        "\nComplaint Type: ${complaintSelectedValue}",
//                Toast.LENGTH_LONG).show()

            val userComplaint = hashMapOf(
                "address" to address,
                "comment" to complaintReason,
                "complainant_name" to complainantName,
                "complaint_type" to complaintSelectedValue,
                "complaint_status" to complaintStatusValue,
                "contact_number" to contactNumber,
                "date" to calendar.time,
                "email" to email,
                "house_num" to complainantHouseNum,
                "user_id" to userID
            )

            val complaintRef = db.collection("complaints").document()
            userComplaint["complaint_id"] = complaintRef.id

            // Add Complaint
            complaintRef
                .set(userComplaint, SetOptions.merge())

                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    val view = View.inflate(this@ResidentAddComplaintActivity, R.layout.success_dialog_add_complaint, null)
                    val builder = AlertDialog.Builder(this@ResidentAddComplaintActivity)
                    builder.setView(view)

                    val dialog = builder.create()
                    dialog.show()

                    view.complaintOkBtn.setOnClickListener {
                        dialog.dismiss()
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                        val intent = Intent(this, ResidentViewComplaintActivity::class.java)
                        startActivity(intent)
                    }
                }

                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                }
        }
    }
    // COMPLAINT IMAGE

    private fun dateOfIncident() {
        val calendar = Calendar.getInstance()

        // For Date Picker
        complaintDateIncident.setOnClickListener {
            val calendarYear = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // val months = monthOfYear + 1
                    complaintDateIncident.text =
                        getString(R.string.date_format, monthOfYear + 1, dayOfMonth, year)

                    complaintYear = year
                    complaintMonth = monthOfYear
                    complaintDay = dayOfMonth
                }, calendarYear, month, day
            )

            // Disable previous date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }

        // For Time Picker
        complaintTimeIncident.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, hourOfDay, minute ->
                complaintTimeIncident.text = getString(R.string.minute_format, hourOfDay, minute)
                complaintHour = hourOfDay
                complaintMinute = minute
            }, startHour, startMinute, false).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}