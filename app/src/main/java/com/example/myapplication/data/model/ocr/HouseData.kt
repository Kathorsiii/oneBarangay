package com.example.myapplication.data.model.ocr

import com.google.gson.annotations.SerializedName

data class HouseData(
    val address: Address,
    @SerializedName("date")
    val date_accomplished: Date,
    @SerializedName("household_no.")
    val house_num: HouseholdNo,
)