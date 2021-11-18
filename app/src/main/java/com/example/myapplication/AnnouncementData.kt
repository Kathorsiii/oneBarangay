package com.example.myapplication

import com.google.firebase.Timestamp

data class AnnouncementData(
    // var announcement_id: String ?= null,
    var title: String ?= null,
    var body: String ?= null,
    var creation_date: Timestamp ?= null,
    var thumbnail: String ?= null,
)