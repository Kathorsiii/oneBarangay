package com.example.myapplication.data.model

import com.google.firebase.Timestamp

data class FirestoreRBI(
    val address: String? = null,
    val date_accomplished: Timestamp? = null,
    val house_num: String? = null,
    val family_name: String? = null,
    val street: String? = null,
    val creation_date: Timestamp? = null,
)
