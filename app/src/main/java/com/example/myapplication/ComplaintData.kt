package com.example.myapplication

data class ComplaintData(
    var complainant_name: String ?= null,
    var complaint_type: String ?= null,
    var complaint_status: String ?= null,
//    var complaint_date: String ?= null,
    var image_url: String ?= null,
)

