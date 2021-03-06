package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.net.Uri.fromFile
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.databinding.ActivityResidentReserveAppointmentBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.sucess_dialog_reserve_appointment.*
import kotlinx.android.synthetic.main.sucess_dialog_reserve_appointment.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import retrofit2.Response
import retrofit2.Retrofit
import com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_resident_add_complaint.*
import kotlinx.android.synthetic.main.notification.*
import kotlinx.android.synthetic.main.success_dialog_add_complaint.view.*
import android.app.*
import kotlinx.android.synthetic.main.activity_resident_reserve_appointment.*

class ResidentReserveAppointmentActivity : AppCompatActivity() {

    private lateinit var notificationBody: NotificationBody

    // Notification
    var title = ""
    var message = ""
    private lateinit var userToken: String

    // Binding
    private lateinit var binding: ActivityResidentReserveAppointmentBinding

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private val requestedDocuments: MutableList<Map<String, Any>> = ArrayList()
    private val documentNames: MutableList<String> = ArrayList()

    private var storage: FirebaseStorage = Firebase.storage("gs://onebarangay-media")
    private var downloadUri: Uri = Uri.parse("")

    // For Checkbox
    private var appointmentYear: Int = 0
    private var appointmentMonth: Int = 0
    private var appointmentDay: Int = 0
    private var appointmentHour: Int = 0
    private var appointmentMinute: Int = 0

    // ActionBar
    private lateinit var actionBar: ActionBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResidentReserveAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Schedule Appointment"

        // Back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading Image...")
        progressDialog.setMessage("Wait while loading...")
        progressDialog.setCancelable(false)

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        val db = Firebase.firestore

        val userID = firebaseAuth.uid!!

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            userToken = task.result!!

            // Log and toast
//            val generatedToken = getString(R.string.msg_token_fmt, userToken)
//            Log.d(TAG, generatedToken)
//            Toast.makeText(baseContext, generatedToken, Toast.LENGTH_SHORT).show()
////
//            println(generatedToken)

            db.collection("users").document(userID)
                .set(mapOf(
                    "mobile_notification" to userToken,
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

        binding.reserveDoneBtn.setOnClickListener {
            for (documentName in documentNames) {
                requestedDocuments.add(
                    mapOf(
                        "document_name" to documentName,
                        "ready_issue" to false,
                        "slugify" to documentName.toSlug(),
                        "info_status" to false
                    )
                )
            }

            val appointmentPurpose = binding.appointmentPurposeInput.text.toString()

            // Datetime
            val calendar = Calendar.getInstance()
            calendar.set(
                appointmentYear,
                appointmentMonth,
                appointmentDay,
                appointmentHour,
                appointmentMinute
            )
            val startAppointment = calendar.time
            val timeInSecs: Long = calendar.timeInMillis
            // Add 15 minutes to startAppointment.
            val endAppointment = Date(timeInSecs + (15 * 60 * 1000))
            val currentTime = Calendar.getInstance().time

            val userID = firebaseAuth.uid!!
            val db = Firebase.firestore

            val userRef = db.collection("users").document(userID)
            var userData: Map<String, Any>

//            db.collection("document_request").document(userID)
//                .get()
//                .addOnSuccessListener { document ->
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    binding.dateAndTimeText.visibility = View.VISIBLE
//                    binding.dateAndTimeSelected.visibility = View.VISIBLE
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userData = document.data!!

                        val docRequestRef = db.collection("document_request").document()
                        val docRequestData = hashMapOf(
                            "appointment_image" to downloadUri.toString(),
                            "appointment_purpose" to appointmentPurpose,
                            "created_on" to currentTime,
                            "document" to requestedDocuments,
                            "first_name" to userData["first_name"],
                            "middle_name" to userData["middle_name"],
                            "last_name" to userData["last_name"],
                            "contact_number" to userData["contact_number"],
                            "address" to userData["address"],
                            "role" to userData["role"],
                            "email" to userData["email"],
                            "photo_url" to userData["photo_url"],
                            "start_appointment" to startAppointment,
                            "end_appointment" to endAppointment,
                            "user_verified" to false,
                            "status" to "request",
                            "user_id" to userID,
                            "document_id" to docRequestRef.id
                        )

                        docRequestRef.set(docRequestData, SetOptions.merge())
                            .addOnSuccessListener {

                                userRef.collection("document_request").document(docRequestRef.id)
                                    .set(docRequestData)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "Document Request: Success")

                                        val view =
                                            View.inflate(this@ResidentReserveAppointmentActivity,
                                                R.layout.sucess_dialog_reserve_appointment,
                                                null)
                                        val builder =
                                            AlertDialog.Builder(this@ResidentReserveAppointmentActivity)
                                        builder.setView(view)
                                        val dialog = builder.create()
                                        dialog.show()

                                        view.reserveAppt_ok_btn.setOnClickListener {
                                            dialog.dismiss()
                                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                                            val intent =
                                                Intent(this, ViewAppointmentActivity::class.java)
                                            startActivity(intent)
                                        }
                                        sendNotification()
                                    }

                                    .addOnFailureListener {
                                        Log.d(TAG, "Document Request: Failure")
                                    }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    "Appointment Schedule Failed $e",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Toast.makeText(this, "No such document", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "get failed with $exception", Toast.LENGTH_LONG).show()
                }
        }

        binding.reserveCancelBtn.setOnClickListener {
            val intent = Intent(this, ViewAppointmentActivity::class.java)
            startActivity(intent)
        }

        binding.uploadImageOrTakePic.setOnClickListener {
            openImagePicker()
        }

        binding.residentReserveApptDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val calendarYear = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.residentReserveApptDate.text = getString(
                        R.string.date_format, monthOfYear + 1, dayOfMonth, year,
                    )
                    appointmentYear = year
                    appointmentMonth = monthOfYear
                    appointmentDay = dayOfMonth
                }, calendarYear, month, day
            )

            // Disable previous date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }

        binding.residentReserveApptTime.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, hourOfDay, minute ->
                binding.residentReserveApptTime.text =
                    getString(R.string.minute_format, hourOfDay, minute)
                appointmentHour = hourOfDay
                appointmentMinute = minute
            }, startHour, startMinute, false).show()
        }
    }

    // Notification
    private fun sendNotification() {
        val notifService = NotificationRetrofitInstance
            .getRetrofitInstance()
            .create(NotificationRetrofitInterface::class.java)

//        val response = notifService.sendNotification(NotificationBody)

        val responseLiveData: LiveData<Response<NotificationBody>> = liveData {
            val response =
                notifService.sendNotification(NotificationBody("Reserve Added Successfully",
                    "You have successfully scheduled a reservation",
                    userToken))
            emit(response)
        }

        responseLiveData.observe(this, {
            notificationBody = it.body()!!
            println(notificationBody)
        })
    }

    // Open Image Picker
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
                binding.appointmentImage.setImageURI(fileUri)

                val storageRef = storage.reference
                val file = fromFile(File(fileUri.path!!))
                val imagesRef = storageRef.child("${file.lastPathSegment}")
                val uploadTask = imagesRef.putFile(file)

                progressDialog.show()

                uploadTask.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }.addOnSuccessListener {
                    val urlTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                        imagesRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss();
                            downloadUri = task.result!!
                            Toast.makeText(this, "Upload Successfully!", Toast.LENGTH_LONG).show()
                            setViewVisibility()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
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

    private fun setViewVisibility() {
        binding.verificationTitle.visibility = View.VISIBLE
        binding.appointmentImage.visibility = View.VISIBLE
        binding.reserveDoneBtn.visibility = View.VISIBLE
        binding.reserveCancelBtn.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            when (view.id) {
                R.id.docuIndigency -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.docuLocalEmployment -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.docuVerification -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.docuClearance -> {
                    if (checked) {
                        documentNames.add(view.text.toString())

                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.docuCedula -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.brgyBorrow -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
                R.id.brgyCert -> {
                    if (checked) {
                        documentNames.add(view.text.toString())
                    } else {
                        documentNames.remove(view.text.toString())
                    }
                }
            }
        }
    }

    private fun String.toSlug() = lowercase(Locale.getDefault())
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-")
}