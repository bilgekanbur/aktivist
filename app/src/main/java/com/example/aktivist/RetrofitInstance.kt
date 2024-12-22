package com.example.aktivist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://app.ticketmaster.com/"

    val api: TicketMasterApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON dönüştürme
            .build()
            .create(TicketMasterApiService::class.java)
    }
}
