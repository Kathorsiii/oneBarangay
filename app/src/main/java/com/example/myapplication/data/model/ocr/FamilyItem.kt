package com.example.myapplication.data.model.ocr

import com.google.gson.annotations.SerializedName

data class FamilyItem(
    val citizenship: Citizenship?,
    val civil_status: CivilStatus?,
    val date_of_birth: DateOfBirth?,
    val ext: Ext?,
    @SerializedName("first_name")
    val first_name: FirstNamePangalan?,
    @SerializedName("last_name")
    val last_name: LastNameApelyido?,
    val middle_name: MiddleName?,
    val monthly_income: MonthlyIncome?,
    @SerializedName("place_of_birth")
    val birth_place: PlaceOfBirth?,
    val remarks: Remarks?,
    @SerializedName("sex_m_or_f")
    val sex: SexMOrF?,
)
