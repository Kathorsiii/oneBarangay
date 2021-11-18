package com.example.myapplication

import com.google.firebase.Timestamp

data class ComplaintData(
    var complainant_name: String ?= null,
    var complaint_type: String ?= null,
    var complaint_status: String ?= null,
    var date: Timestamp ?= null,
    var comment: String ? = null,
    var contact_number: String ?= null,
    var image_url: String ?= null,
)

