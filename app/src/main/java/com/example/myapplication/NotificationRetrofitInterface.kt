package com.example.myapplication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationRetrofitInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/notification/")
    suspend fun sendNotification(@Body notification: NotificationBody): Response<NotificationBody>

}