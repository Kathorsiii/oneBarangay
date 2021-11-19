package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivityResidentReserveAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_resident_reserve_appointment.*
import kotlinx.android.synthetic.main.activity_scan_document.*
import java.util.*

class ResidentReserveAppointmentActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityResidentReserveAppointmentBinding

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    // For Checkbox
    lateinit var cb_docuIndigency: CheckBox
    lateinit var cb_docuLocalEmployment: CheckBox
    lateinit var cb_docuVerification: CheckBox
    lateinit var cb_docuClearance: CheckBox
    lateinit var cb_docuCedula: CheckBox
    lateinit var cb_brgyBorrow: CheckBox
    lateinit var cb_brgyCert: CheckBox


    // ActionBar
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_reserve_appointment)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Reserve Appointment"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        reserveDoneBtn.setOnClickListener {
            testClick()
        }

        reserveAppointment()

    }

    private fun reserveAppointment() {

        // For Date Picker
        setDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val months = monthOfYear + 1
                    residentReserveAppt_Date.setText("$months / $dayOfMonth / $year")
                },
                year,
                month,
                day)

            // Disable previous date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }

        // For Time Picker
        setTimeBtn.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                residentReserveAppt_Time.setText("$hourOfDay:$minute")
            }, startHour, startMinute, false).show()
        }

        cb_docuIndigency = findViewById(R.id.docuIndigency)


        val appointmentPurpose: TextView = findViewById(R.id.appointmentPurposeInput)
        val appointmentImage: ImageView = findViewById(R.id.appointmentImage)

        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {

            val db = Firebase.firestore

            val userID = firebaseAuth.uid!!

            // println(userID)

            val getAppointment = db.collection("appointments").document(userID)

            val appointments = hashMapOf(
//                "appointment_image" to appointmentImage,
                "appointment_purpose" to appointmentPurpose,
//                "contact_no" to contactNumber,
//                "created_on" to createdDate,
//                "document" to document,
//                "document_id" to,
//                "end_appointment" to,
//                "first_name"to ,
//                "last_name" to,
//                "middle_name" to,
//                "start_appointment" to,
//                "status" to,
//                "user_uid" to ,

            )


        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun testClick() {
        if (view is CheckBox) {
            val checked: Boolean = (view as CheckBox).isChecked

            when (view.id) {
                R.id.docuIndigency -> {
                    if (checked) {
                        Toast.makeText(this, "You clicked document indigency", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        // Remove the meat
                    }
                }
                R.id.docuLocalEmployment -> {
                    if (checked) {
                        Toast.makeText(this,
                            "You clicked document local employment",
                            Toast.LENGTH_LONG).show()
                    } else {
                        // I'm lactose intolerant
                    }
                }
                // TODO: Veggie sandwich
            }
        }
    }
}