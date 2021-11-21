package com.example.myapplication

import android.app.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.databinding.ActivityResidentAddComplaintBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_resident_add_complaint.*
import kotlinx.android.synthetic.main.notification.*
import kotlinx.android.synthetic.main.success_dialog_add_complaint.view.*
import org.json.JSONArray
import java.io.File
import java.util.*
import retrofit2.Response

class ResidentAddComplaintActivity : AppCompatActivity() {

    private lateinit var notificationBody: NotificationBody

    // Notification
    var title = ""
    var message = ""

    // Binding
    private lateinit var binding: ActivityResidentAddComplaintBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

//    private lateinit var generatedToken: String

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

    private var storage: FirebaseStorage = Firebase.storage("gs://onebarangay-media")
    private lateinit var downloadUri: Uri

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_resident_add_complaint)

        binding = ActivityResidentAddComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Add Complaint"

        // ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Uploading image...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Complaint Cancel Button
        complaintCancelBtn.setOnClickListener {
            startActivity(Intent(this, ViewComplaintActivity::class.java))
        }

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!
        // Toast.makeText(this, "User ID: ${userID}", Toast.LENGTH_LONG).show()
        // println(userID)

        uploadComplaintProofImage.setOnClickListener {
            openImagePicker()
        }

        // For Complaint Type Dropdown
        val complaintList = resources.getStringArray(R.array.complaint_list)
        val complaintTypeAdapter =
            ArrayAdapter(this, R.layout.complaint_list_item, complaintList)
        binding.complaintTypeInput.setAdapter(complaintTypeAdapter)

        // For Complaint Status Dropdown
        val complaintStatus = resources.getStringArray(R.array.complaint_status_list)
        val complaintStatusAdapter =
            ArrayAdapter(this, R.layout.complaint_status_list_item, complaintStatus)
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

        val getUserRef = db.collection("users").document(userID)

        getUserRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    userData = document.data!!

                    // println(userData)
                    var firstName = userData["first_name"]
                    var lastName = userData["last_name"]
                    complainantName = "$firstName $lastName"

                    // println(complainantName)

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

                        var complaintReason = complaintReasonInput.text.toString()

                        val calendar = Calendar.getInstance()
                        calendar.set(
                            complaintYear,
                            complaintMonth,
                            complaintDay,
                            complaintHour,
                            complaintMinute
                        )

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

                        val complaintRef = db.collection("complaints").document()

                        val userComplaint = hashMapOf(
                            "address" to address,
                            "comment" to complaintReason,
                            "complainant_name" to complainantName,
                            "complaint_type" to complaintSelectedValue,
                            "complaint_status" to complaintStatusValue,
                            "contact_number" to contactNumber,
                            "date" to calendar.time,
                            "email" to email,
//                            "house_num" to complainantHouseNum,
                            "image_url" to downloadUri.toString(),
                            "user_id" to userID,
                            "complaint_id" to complaintRef.id
                        )

                        // Add Complaint
                        complaintRef.set(userComplaint, SetOptions.merge())
                            .addOnSuccessListener {
                                userRef.collection("complaints").document(complaintRef.id)
                                    .set(userComplaint)

                                    .addOnSuccessListener {

                                        Log.d(TAG, "DocumentSnapshot successfully written!")

                                        val view = View.inflate(this@ResidentAddComplaintActivity,
                                            R.layout.success_dialog_add_complaint,
                                            null)
                                        val builder =
                                            AlertDialog.Builder(this@ResidentAddComplaintActivity)
                                        builder.setView(view)

                                        val dialog = builder.create()
                                        dialog.show()

                                        view.complaintOkBtn.setOnClickListener {
                                            dialog.dismiss()
                                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                                            val intent =
                                                Intent(this, ViewComplaintActivity::class.java)
                                            startActivity(intent)
                                        }
                                        sendNotification()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error writing document", e)
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing document", e)
                            }
                    } else {
                        Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // Notification
    private fun sendNotification() {
        val firebaseUser = firebaseAuth.currentUser

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!

        val notifService = NotificationRetrofitInstance
            .getRetrofitInstance()
            .create(NotificationRetrofitInterface::class.java)

        val responseLiveData: LiveData<Response<NotificationBody>> = liveData {
            val response =
                notifService.sendNotification(NotificationBody("Complaint Added Successfully",
                    "You have successfully added a complaint",
                    "dOBHIOtFS0OvsTkW3LL_uy:APA91bHlP_HRRm7of1XFiuyLuA04cyJb071DZqdeuuIjcB_5VjY_R-7i-oUyGOrESW_XYOUUXkTWCTYs4DVfbES4NF6YpbKV0KqUh7OtDjKlXQMHpcLyiP5Kr_ZUnZAKE0tZebmXLo2G"))
            emit(response)
        }

        responseLiveData.observe(this, {
            notificationBody = it.body()!!
            println(notificationBody)
        })

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result!!

            // Log and toast
//            val generatedToken = getString(R.string.msg_token_fmt, token)
//            Log.d(TAG, generatedToken)
//            Toast.makeText(baseContext, generatedToken, Toast.LENGTH_SHORT).show()

//            println(generatedToken)

            db.collection("users").document(userID)
                .set(mapOf(
                    "mobile_notification" to token,
                ), SetOptions.merge())

            db.collection("users").document(userID)
                .collection("notification")
                .add(mapOf(
                    "title" to title,
                    "body" to message,
                    "icon" to "https://storage.googleapis.com/onebarangay-malanday/assets/img/favicon/favicon.ico",
                    "requireInteraction" to true
                ))
        })
    }

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

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080,
                1080)    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data!!.data!!
                binding.complaintSelectedImg.setImageURI(fileUri)
                binding.complaintSelectedImg.visibility = View.VISIBLE
//                setViewVisibility()

                val storageRef = storage.reference
                val file = Uri.fromFile(File(fileUri.path!!))
                val imagesRef = storageRef.child("${file.lastPathSegment}")
                val uploadTask = imagesRef.putFile(file)

                progressDialog.show()

                uploadTask.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }.addOnSuccessListener { taskSnapshot ->

                    val filename = taskSnapshot.metadata!!.name

                    val urlTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                        imagesRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            progressDialog.dismiss()

                            // Photo URL
                            downloadUri = task.result!!
                            Toast.makeText(this,
                                "Successfully added image!",
                                Toast.LENGTH_LONG).show()

                            binding.complaintDoneBtn.visibility = View.VISIBLE
                            binding.complaintCancelBtn.visibility = View.VISIBLE

                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }

            else -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.task_cancelled),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}