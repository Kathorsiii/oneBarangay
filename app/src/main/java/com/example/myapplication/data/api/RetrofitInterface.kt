package com.example.myapplication.data.api

import com.example.myapplication.data.model.data_viz.DataViz
//import com.example.myapplication.data.model.ocr.RBI
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("/api/data_viz/")
    suspend fun getDataViz(): Response<DataViz>

//    @GET("/api/ocr/2.png")
//    suspend fun getOcr(): Response<RBI>
}