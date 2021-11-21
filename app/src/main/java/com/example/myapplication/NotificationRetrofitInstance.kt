package com.example.myapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationRetrofitInstance {
    companion object {
        private const val BASE_URL = "http://1a4d-180-190-212-169.ngrok.io/"

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}