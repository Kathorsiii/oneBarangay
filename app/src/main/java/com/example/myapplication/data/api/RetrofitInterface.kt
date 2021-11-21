package com.example.myapplication.data.api

import com.example.myapplication.data.model.data_viz.DataViz
import com.example.myapplication.data.model.ocr.RBI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
    @GET("/api/data_viz/")
    suspend fun getDataViz(): Response<DataViz>

    @GET("/api/ocr/{filename}")
    suspend fun getOcr(@Path(value = "filename") filename: String): Response<RBI>
}