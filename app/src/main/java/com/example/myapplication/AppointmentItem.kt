package com.example.myapplication

import com.google.firebase.Timestamp

data class AppointmentItem(
    var first_name: String? = null,
    var last_name: String? = null,
    var document_id: String? = null,
    var appointment_image: String? = null,
    var appointment_purpose: String? = null,
    var document: ArrayList<Map<String, Any>>? = null,
    var contact_number: String? = null,
    var email: String? = null,
    var status: String? = null,
    var start_appointment: Timestamp? = null,
    var end_appointment: Timestamp? = null,
)
